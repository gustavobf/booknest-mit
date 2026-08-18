package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias")
public class CategoriaController extends BaseCrudController<Categoria> {
    private final CategoriaService categoriaService;

    public CategoriaController (CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    protected BaseService<Categoria> getService () {
        return categoriaService;
    }

    @Override
    protected Integer getId (Categoria entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Categoria entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Categoria";
    }
}
