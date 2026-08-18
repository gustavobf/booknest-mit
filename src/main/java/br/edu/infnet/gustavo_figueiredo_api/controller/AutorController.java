package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@Tag(name = "Autores")
public class AutorController extends BaseCrudController<Autor> {
    private final AutorService autorService;

    public AutorController (AutorService autorService) {
        this.autorService = autorService;
    }

    @Override
    protected BaseService<Autor> getService () {
        return autorService;
    }

    @Override
    protected Integer getId (Autor entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Autor entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Autor";
    }
}
