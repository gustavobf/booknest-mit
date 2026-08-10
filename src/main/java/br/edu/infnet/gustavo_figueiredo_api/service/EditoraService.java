package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class EditoraService extends BaseService<Editora> {
    @Override
    protected Integer obterId (Editora entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Editora entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Editora entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Editora não pode ser nula.");
        }
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir nome.");
        }
        if (entidade.getCidade() == null || entidade.getCidade().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir cidade.");
        }
        if (entidade.getEmailContato() == null || entidade.getEmailContato().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir email de contato.");
        }
        if (entidade.getAtiva() == null) {
            throw new DadosInvalidosException("Editora deve informar se está ativa.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Editora";
    }

    public List<Editora> listarAtivas () {
        return obterLista().stream().filter(editora -> Boolean.TRUE.equals(editora.getAtiva())).toList();
    }
}
