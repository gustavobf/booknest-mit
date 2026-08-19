package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
public class EmprestimoService extends BaseService<Emprestimo> {
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExemplarRepository exemplarRepository;

    public EmprestimoService (EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository,
                              ExemplarRepository exemplarRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Override
    protected JpaRepository<Emprestimo, Integer> getRepository () {
        return emprestimoRepository;
    }

    @Override
    protected Integer obterId (Emprestimo entidade) {
        return entidade.getId();
    }

    @Override
    protected void validarEntidade (Emprestimo entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Empréstimo não pode ser nulo.");
        }
        if (entidade.getUsuario() == null || entidade.getUsuario().getId() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir usuário.");
        }
        if (entidade.getExemplar() == null || entidade.getExemplar().getId() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir exemplar.");
        }
        if (entidade.getDataEmprestimo() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir data de empréstimo.");
        }
        if (entidade.getDataEsperadaDevolucao() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir data esperada de devolução.");
        }
        if (entidade.getMulta() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir multa.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Empréstimo";
    }

    @Override
    @Transactional
    public Emprestimo incluir (Emprestimo entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        Emprestimo emprestimo = super.incluir(entidade);
        if (!emprestimo.estaDevolvido()) {
            Exemplar exemplar = emprestimo.getExemplar();
            exemplar.setDisponivel(false);
            exemplarRepository.save(exemplar);
        }
        return emprestimo;
    }

    @Override
    @Transactional
    public Emprestimo alterar (Emprestimo entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        return super.alterar(entidade);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarEmAberto () {
        return emprestimoRepository.findByDataDevolucaoIsNull();
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarAtrasados () {
        return emprestimoRepository.findAtrasados(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarNaoAtrasados () {
        return emprestimoRepository.findNaoAtrasados(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarPorSituacaoAtraso (Boolean atrasado) {
        if (atrasado == null) {
            return obterLista();
        }
        return atrasado ? listarAtrasados() : listarNaoAtrasados();
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarPorUsuario (Integer idUsuario) {
        return emprestimoRepository.findByUsuarioId(idUsuario);
    }

    @Transactional
    public Emprestimo registrarDevolucao (Integer idEmprestimo, LocalDate dataDevolucao, Double multa) {
        if (dataDevolucao == null) {
            throw new DadosInvalidosException("Data de devolução é obrigatória.");
        }
        if (multa == null || multa < 0) {
            throw new DadosInvalidosException("Multa deve ser informada e não pode ser negativa.");
        }

        Emprestimo emprestimo = obterPorId(idEmprestimo);
        if (emprestimo.estaDevolvido()) {
            throw new OperacaoNaoPermitidaException("Empréstimo " + idEmprestimo + " já foi devolvido.");
        }

        emprestimo.registrarDevolucao(dataDevolucao, multa);
        Exemplar exemplar = emprestimo.getExemplar();
        exemplar.setDisponivel(true);
        exemplarRepository.save(exemplar);
        return emprestimoRepository.save(emprestimo);
    }

    private void prepararRelacionamentos (Emprestimo emprestimo) {
        Integer idUsuario = emprestimo.getUsuario().getId();
        Integer idExemplar = emprestimo.getExemplar().getId();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id " + idUsuario + " não encontrado."));
        Exemplar exemplar = exemplarRepository.findById(idExemplar).orElseThrow(
                () -> new RegistroNaoEncontradoException("Exemplar com id " + idExemplar + " não encontrado."));

        emprestimo.setUsuario(usuario);
        emprestimo.setExemplar(exemplar);
    }
}
