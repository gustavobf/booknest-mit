package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ExemplarService extends BaseService<Exemplar> {
    @Override
    protected Integer obterId (Exemplar entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Exemplar entidade, Integer id) {
        entidade.setId(id);
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
        if (entidade.getLivro() == null) {
            throw new DadosInvalidosException("Exemplar deve estar associado a um livro.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Exemplar";
    }

    public List<Exemplar> listarDisponiveis () {
        return obterLista().stream().filter(exemplar -> Boolean.TRUE.equals(exemplar.getDisponivel())).toList();
    }

    public List<Exemplar> listarPorLivro (Integer idLivro) {
        return obterLista().stream()
                .filter(exemplar -> exemplar.getLivro() != null && exemplar.getLivro().getId().equals(idLivro))
                .toList();
    }
}
