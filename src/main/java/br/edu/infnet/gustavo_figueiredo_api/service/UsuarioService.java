package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class UsuarioService extends BaseService<Usuario> {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected JpaRepository<Usuario, Integer> getRepository () {
        return usuarioRepository;
    }

    @Override
    protected Integer obterId (Usuario entidade) {
        return entidade.getId();
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

    @Transactional(readOnly = true)
    public List<Usuario> listarAtivos () {
        return usuarioRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarInativos () {
        return usuarioRepository.findByAtivoFalse();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarPorAtivo (Boolean ativo) {
        if (ativo == null) {
            return obterLista();
        }
        return ativo ? listarAtivos() : listarInativos();
    }
}
