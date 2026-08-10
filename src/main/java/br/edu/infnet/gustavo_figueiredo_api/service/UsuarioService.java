package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UsuarioService extends BaseService<Usuario> {
    @Override
    protected Integer obterId (Usuario entidade) {
        return entidade.getId();
    }

    @Override
    protected void definirId (Usuario entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected void validarEntidade (Usuario entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Usuário não pode ser nulo.");
        }
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new DadosInvalidosException("Usuário deve possuir nome.");
        }
        if (entidade.getEmail() == null || entidade.getEmail().isBlank()) {
            throw new DadosInvalidosException("Usuário deve possuir email.");
        }
        if (entidade.getMatricula() == null || entidade.getMatricula().isBlank()) {
            throw new DadosInvalidosException("Usuário deve possuir matrícula.");
        }
        if (entidade.getAtivo() == null) {
            throw new DadosInvalidosException("Usuário deve informar se está ativo.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Usuário";
    }

    public List<Usuario> listarAtivos () {
        return obterLista().stream().filter(usuario -> Boolean.TRUE.equals(usuario.getAtivo())).toList();
    }
}
