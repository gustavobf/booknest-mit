package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Empréstimos")
public class EmprestimoController extends BaseCrudController<Emprestimo> {
    private final EmprestimoService emprestimoService;

    public EmprestimoController (EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @Override
    protected BaseService<Emprestimo> getService () {
        return emprestimoService;
    }

    @Override
    protected Integer getId (Emprestimo entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Emprestimo entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Empréstimo";
    }
}
