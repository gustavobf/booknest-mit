package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class ExemplarService extends BaseService<Exemplar> {
    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    public ExemplarService (ExemplarRepository exemplarRepository, LivroRepository livroRepository) {
        this.exemplarRepository = exemplarRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    protected JpaRepository<Exemplar, Integer> getRepository () {
        return exemplarRepository;
    }

    @Override
    protected Integer obterId (Exemplar entidade) {
        return entidade.getId();
    }

    @Override
    protected void validarEntidade (Exemplar entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Exemplar não pode ser nulo.");
        }
        if (entidade.getCodigo() == null || entidade.getCodigo().isBlank()) {
            throw new DadosInvalidosException("Exemplar deve possuir código.");
        }
        if (entidade.getEstadoConservacao() == null) {
            throw new DadosInvalidosException("Exemplar deve possuir estado de conservação.");
        }
        if (entidade.getDisponivel() == null) {
            throw new DadosInvalidosException("Exemplar deve informar disponibilidade.");
        }
        if (entidade.getLivro() == null || entidade.getLivro().getId() == null) {
            throw new DadosInvalidosException("Exemplar deve estar associado a um livro.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Exemplar";
    }

    @Override
    @Transactional
    public Exemplar incluir (Exemplar entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        return super.incluir(entidade);
    }

    @Override
    @Transactional
    public Exemplar alterar (Exemplar entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        return super.alterar(entidade);
    }

    @Transactional(readOnly = true)
    public List<Exemplar> listarDisponiveis () {
        return exemplarRepository.findByDisponivelTrue();
    }

    @Transactional(readOnly = true)
    public List<Exemplar> listarIndisponiveis () {
        return exemplarRepository.findByDisponivelFalse();
    }

    @Transactional(readOnly = true)
    public List<Exemplar> listarPorDisponibilidade (Boolean disponivel) {
        if (disponivel == null) {
            return obterLista();
        }
        return disponivel ? listarDisponiveis() : listarIndisponiveis();
    }

    @Transactional(readOnly = true)
    public List<Exemplar> listarPorLivro (Integer idLivro) {
        return exemplarRepository.findByLivroId(idLivro);
    }

    private void prepararRelacionamentos (Exemplar exemplar) {
        Integer idLivro = exemplar.getLivro().getId();
        Livro livro = livroRepository.findById(idLivro)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Livro com id " + idLivro + " não encontrado."));
        exemplar.setLivro(livro);
    }
}
