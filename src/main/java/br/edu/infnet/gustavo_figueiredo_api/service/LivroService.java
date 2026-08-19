package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class LivroService extends BaseService<Livro> {
    @Override
    protected Integer obterId (Livro entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Livro entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Livro entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Livro não pode ser nulo.");
        }
        if (entidade.getTitulo() == null || entidade.getTitulo().isBlank()) {
            throw new DadosInvalidosException("Livro deve possuir título.");
        }
        if (entidade.getIsbn() == null || entidade.getIsbn().isBlank()) {
            throw new DadosInvalidosException("Livro deve possuir ISBN.");
        }
        if (entidade.getAutor() == null) {
            throw new DadosInvalidosException("Livro deve possuir autor.");
        }
        if (entidade.getCategoria() == null) {
            throw new DadosInvalidosException("Livro deve possuir categoria.");
        }
        if (entidade.getEditora() == null) {
            throw new DadosInvalidosException("Livro deve possuir editora.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Livro";
    }

    public List<Livro> listarOrdenadosPorTitulo () {
        return obterLista().stream().sorted((l1, l2) -> l1.getTitulo().compareToIgnoreCase(l2.getTitulo())).toList();
    }

    public List<Livro> listarDisponiveis () {
        return obterLista().stream().filter(livro -> Boolean.TRUE.equals(livro.getDisponivel())).toList();
    }

    public List<Livro> listarIndisponiveis () {
        return obterLista().stream().filter(livro -> Boolean.FALSE.equals(livro.getDisponivel())).toList();
    }

    public List<Livro> listarPorDisponibilidade (Boolean disponivel) {
        if (disponivel == null) {
            return obterLista();
        }
        return disponivel ? listarDisponiveis() : listarIndisponiveis();
    }

    public List<Livro> buscarPorAutor (Integer idAutor) {
        return obterLista().stream()
                .filter(livro -> livro.getAutor() != null && livro.getAutor().getId().equals(idAutor)).toList();
    }

    public List<String> listarTitulosMaiusculos () {
        return obterLista().stream().map(Livro::getTitulo).map(String::toUpperCase).toList();
    }
}
