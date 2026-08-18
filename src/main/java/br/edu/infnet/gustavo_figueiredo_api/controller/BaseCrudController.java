package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import java.net.*;
import java.util.*;

public abstract class BaseCrudController<T extends Entidade> {

    protected abstract BaseService<T> getService ();

    protected abstract Integer getId (T entidade);

    protected abstract void setId (T entidade, Integer id);

    protected abstract String getNomeEntidade ();

    @GetMapping
    public ResponseEntity<List<T>> listar () {
        return ResponseEntity.ok(getService().obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> obterPorId (@PathVariable Integer id) {
        return ResponseEntity.ok(getService().obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<T> incluir (@RequestBody T entidade) {
        T entidadeCriada = getService().incluir(validarEntidade(entidade));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(getId(entidadeCriada)).toUri();
        return ResponseEntity.created(location).body(entidadeCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> alterar (@PathVariable Integer id, @RequestBody T entidade) {
        T entidadeValida = validarEntidade(entidade);
        setId(entidadeValida, id);
        return ResponseEntity.ok(getService().alterar(entidadeValida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir (@PathVariable Integer id) {
        getService().excluir(id);
        return ResponseEntity.noContent().build();
    }

    private T validarEntidade (T entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException(getNomeEntidade() + " não pode ser nulo.");
        }
        return entidade;
    }
}
