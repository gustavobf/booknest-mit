package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/{id}/emprestimos")
    @Operation(summary = "Listar empréstimos do usuário", description = "Retorna o histórico de empréstimos de um usuário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimos retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Emprestimo.class)))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
    public ResponseEntity<List<Emprestimo>> listarEmprestimosDoUsuario (
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obterPorId(id).getEmprestimos());
    }
}
