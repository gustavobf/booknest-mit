package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AutorService extends BaseService<Autor> {
    @Override
    protected Integer obterId (Autor entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Autor entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Autor entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Autor não pode ser nulo.");
        }
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new DadosInvalidosException("Autor deve possuir nome.");
        }
        if (entidade.getNacionalidade() == null || entidade.getNacionalidade().isBlank()) {
            throw new DadosInvalidosException("Autor deve possuir nacionalidade.");
        }
        if (entidade.getAnoNascimento() == null || entidade.getAnoNascimento() <= 0) {
            throw new DadosInvalidosException("Autor deve possuir ano de nascimento válido.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Autor";
    }

    public List<Autor> listarOrdenadosPorNome () {
        return obterLista().stream().sorted((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome())).toList();
    }

    public List<Autor> buscarPorNacionalidade (String nacionalidade) {
        return obterLista().stream().filter(autor -> autor.getNacionalidade().equalsIgnoreCase(nacionalidade)).toList();
    }
}
