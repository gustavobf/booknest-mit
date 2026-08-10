package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;

import java.util.*;

public abstract class BaseService<T extends Entidade> {
    private final Map<Integer, T> armazenamento = new LinkedHashMap<>();
    private int proximoId = 1;

    public T incluir (T entidade) {
        validarEntidade(entidade);

        Integer id = obterId(entidade);
        if (id == null || id <= 0) {
            id = proximoId++;
            definirId(entidade, id);
        } else {
            proximoId = Math.max(proximoId, id + 1);
        }

        if (armazenamento.containsKey(id)) {
            throw new OperacaoNaoPermitidaException(getNomeEntidade() + " com id " + id + " já existe.");
        }

        armazenamento.put(id, entidade);
        return entidade;
    }

    public T alterar (T entidade) {
        validarEntidade(entidade);

        Integer id = obterId(entidade);
        if (id == null || id <= 0) {
            throw new DadosInvalidosException(getNomeEntidade() + " precisa de um id válido para alteração.");
        }

        if (!armazenamento.containsKey(id)) {
            throw new RegistroNaoEncontradoException(getNomeEntidade() + " com id " + id + " não encontrado.");
        }

        armazenamento.put(id, entidade);
        return entidade;
    }

    public void excluir (Integer id) {
        validarId(id);

        if (armazenamento.remove(id) == null) {
            throw new RegistroNaoEncontradoException(getNomeEntidade() + " com id " + id + " não encontrado.");
        }
    }

    public T obterPorId (Integer id) {
        validarId(id);

        T entidade = armazenamento.get(id);
        if (entidade == null) {
            throw new RegistroNaoEncontradoException(getNomeEntidade() + " com id " + id + " não encontrado.");
        }

        return entidade;
    }

    public List<T> obterLista () {
        return List.copyOf(armazenamento.values());
    }

    protected Collection<T> valores () {
        return armazenamento.values();
    }

    protected Map<Integer, T> getArmazenamento () {
        return armazenamento;
    }

    protected abstract Integer obterId (T entidade);

    protected abstract void definirId (T entidade, Integer id);

    protected abstract void validarEntidade (T entidade);

    protected abstract String getNomeEntidade ();

    private void validarId (Integer id) {
        if (id == null || id <= 0) {
            throw new DadosInvalidosException(getNomeEntidade() + " exige um id válido.");
        }
    }
}
