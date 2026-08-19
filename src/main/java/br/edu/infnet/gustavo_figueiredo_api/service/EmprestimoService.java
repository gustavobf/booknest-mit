package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class EmprestimoService extends BaseService<Emprestimo> {
    @Override
    protected Integer obterId (Emprestimo entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Emprestimo entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Emprestimo entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Empréstimo não pode ser nulo.");
        }
        if (entidade.getUsuario() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir usuário.");
        }
        if (entidade.getExemplar() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir exemplar.");
        }
        if (entidade.getDataEmprestimo() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir data de empréstimo.");
        }
        if (entidade.getMulta() == null) {
            throw new DadosInvalidosException("Empréstimo deve possuir multa.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Empréstimo";
    }

    public List<Emprestimo> listarEmAberto () {
        return obterLista().stream().filter(emprestimo -> !emprestimo.estaDevolvido()).toList();
    }

    public List<Emprestimo> listarAtrasados () {
        return obterLista().stream().filter(Emprestimo::estaAtrasado).toList();
    }

    public List<Emprestimo> listarNaoAtrasados () {
        return obterLista().stream().filter(emprestimo -> !emprestimo.estaAtrasado()).toList();
    }

    public List<Emprestimo> listarPorSituacaoAtraso (Boolean atrasado) {
        if (atrasado == null) {
            return obterLista();
        }
        return atrasado ? listarAtrasados() : listarNaoAtrasados();
    }

    public List<Emprestimo> listarPorUsuario (Integer idUsuario) {
        return obterLista().stream()
                .filter(emprestimo -> emprestimo.getUsuario() != null && emprestimo.getUsuario().getId()
                        .equals(idUsuario)).toList();
    }

    public Emprestimo registrarDevolucao (Integer idEmprestimo, LocalDate dataDevolucao, Double multa) {
        if (dataDevolucao == null) {
            throw new DadosInvalidosException("Data de devolução é obrigatória.");
        }

        Emprestimo emprestimo = obterPorId(idEmprestimo);
        if (emprestimo.estaDevolvido()) {
            throw new OperacaoNaoPermitidaException("Empréstimo " + idEmprestimo + " já foi devolvido.");
        }

        emprestimo.registrarDevolucao(dataDevolucao, multa);
        return emprestimo;
    }
}
