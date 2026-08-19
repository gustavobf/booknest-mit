package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
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

    @Override
    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload do usuário a ser criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class), examples = @ExampleObject(name = "Usuario", value = "{\n  \"nome\": \"Fernanda Alves\",\n  \"email\": \"fernanda.alves@biblioteca.com\",\n  \"matricula\": \"MAT2026999\",\n  \"ativo\": true\n}")))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<Usuario> incluir (@Valid @RequestBody Usuario entidade) {
        return super.incluir(entidade);
    }

    @Override
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna usuários com filtro opcional por status de atividade. Sem parâmetro, retorna todos.")
    @Parameters({
            @Parameter(name = "ativo", in = ParameterIn.QUERY, description = "Filtro opcional: true para ativos, false para inativos. Sem parâmetro retorna todos.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class))))
    public ResponseEntity<List<Usuario>> listar (@RequestParam(name = "ativo", required = false) Boolean ativo) {
        return ResponseEntity.ok(usuarioService.listarPorAtivo(ativo));
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
