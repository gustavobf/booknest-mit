package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários")
public class UsuarioController extends BaseCrudController<Usuario> {
    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected BaseService<Usuario> getService () {
        return usuarioService;
    }

    @Override
    protected Integer getId (Usuario entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Usuario entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Usuário";
    }
}
