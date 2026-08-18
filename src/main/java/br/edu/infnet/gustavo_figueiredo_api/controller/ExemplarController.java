package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exemplares")
@Tag(name = "Exemplares")
public class ExemplarController extends BaseCrudController<Exemplar> {
    private final ExemplarService exemplarService;

    public ExemplarController (ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @Override
    protected BaseService<Exemplar> getService () {
        return exemplarService;
    }

    @Override
    protected Integer getId (Exemplar entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Exemplar entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Exemplar";
    }
}
