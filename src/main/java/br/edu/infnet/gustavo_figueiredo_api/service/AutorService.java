package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class AutorService extends BaseService<Autor> {
    private final AutorRepository autorRepository;

    public AutorService (AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    protected JpaRepository<Autor, Integer> getRepository () {
        return autorRepository;
    }

    @Override
    protected Integer obterId (Autor entidade) {
        return entidade.getId();
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

    @Transactional(readOnly = true)
    public List<Autor> listarOrdenadosPorNome () {
        return autorRepository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarPorNacionalidade (String nacionalidade) {
        return autorRepository.findByNacionalidadeIgnoreCase(nacionalidade);
    }
}
