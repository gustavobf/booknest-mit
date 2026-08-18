package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editoras")
@Tag(name = "Editoras")
public class EditoraController extends BaseCrudController<Editora> {
    private final EditoraService editoraService;

    public EditoraController (EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @Override
    protected BaseService<Editora> getService () {
        return editoraService;
    }

    @Override
    protected Integer getId (Editora entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Editora entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Editora";
    }
}
