package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class CategoriaService extends BaseService<Categoria> {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    protected JpaRepository<Categoria, Integer> getRepository () {
        return categoriaRepository;
    }

    @Override
    protected Integer obterId (Categoria entidade) {
        return entidade.getId();
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

    @Transactional(readOnly = true)
    public List<Categoria> listarOrdenadasPorQuantidadeLivros () {
        return categoriaRepository.findAllOrderByQuantidadeLivrosDesc();
    }
}
