package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

public abstract class BaseService<T extends Entidade> {

    protected abstract JpaRepository<T, Integer> getRepository ();

    protected abstract Integer obterId (T entidade);

    protected abstract void validarEntidade (T entidade);

    protected abstract String getNomeEntidade ();

    @Transactional
    public T incluir (T entidade) {
        validarEntidade(entidade);
        Integer id = obterId(entidade);
        if (id != null) {
            throw new DadosInvalidosException(getNomeEntidade() + " não deve informar id na criação.");
        }
        return getRepository().save(entidade);
    }

    @Transactional
    public T alterar (T entidade) {
        validarEntidade(entidade);
        Integer id = obterId(entidade);
        validarId(id);
        if (!getRepository().existsById(id)) {
            throw new RegistroNaoEncontradoException(getNomeEntidade() + " com id " + id + " não encontrado.");
        }
        return getRepository().save(entidade);
    }

    @Transactional
    public void excluir (Integer id) {
        validarId(id);
        if (!getRepository().existsById(id)) {
            throw new RegistroNaoEncontradoException(getNomeEntidade() + " com id " + id + " não encontrado.");
        }
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    public T obterPorId (Integer id) {
        validarId(id);
        return getRepository().findById(id).orElseThrow(() -> new RegistroNaoEncontradoException(
                getNomeEntidade() + " com id " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<T> obterLista () {
        return getRepository().findAll();
    }

    private void validarId (Integer id) {
        if (id == null || id <= 0) {
            throw new DadosInvalidosException(getNomeEntidade() + " exige um id válido.");
        }
    }
}
