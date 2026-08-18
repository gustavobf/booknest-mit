package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros")
public class LivroController extends BaseCrudController<Livro> {
    private final LivroService livroService;

    public LivroController (LivroService livroService) {
        this.livroService = livroService;
    }

    @Override
    protected BaseService<Livro> getService () {
        return livroService;
    }

    @Override
    protected Integer getId (Livro entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Livro entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Livro";
    }
}
