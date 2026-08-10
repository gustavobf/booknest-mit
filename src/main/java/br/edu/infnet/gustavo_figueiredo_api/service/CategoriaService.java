package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CategoriaService extends BaseService<Categoria> {
    @Override
    protected Integer obterId (Categoria entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Categoria entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Categoria entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Categoria não pode ser nula.");
        }
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new DadosInvalidosException("Categoria deve possuir nome.");
        }
        if (entidade.getDescricao() == null || entidade.getDescricao().isBlank()) {
            throw new DadosInvalidosException("Categoria deve possuir descrição.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Categoria";
    }

    public List<Categoria> listarOrdenadasPorQuantidadeLivros () {
        return obterLista().stream().sorted((c1, c2) -> Long.compare(c2.getLivros().size(), c1.getLivros().size()))
                .toList();
    }
}
