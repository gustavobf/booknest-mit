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

    @Override
    @PostMapping
    @Operation(summary = "Criar editora", description = "Cria uma nova editora.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da editora a ser criada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Editora.class), examples = @ExampleObject(name = "Editora", value = "{\n  \"nome\": \"Intrínseca\",\n  \"cidade\": \"Rio de Janeiro\",\n  \"emailContato\": \"contato@intrinseca.com.br\",\n  \"ativa\": true\n}")))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Editora criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<Editora> incluir (@Valid @RequestBody Editora entidade) {
        return super.incluir(entidade);
    }

    @Override
    @GetMapping
    @Operation(summary = "Listar editoras", description = "Retorna editoras com filtro opcional por status de atividade. Sem parâmetro, retorna todas.")
    @Parameters({
            @Parameter(name = "ativa", in = ParameterIn.QUERY, description = "Filtro opcional: true para ativas, false para inativas. Sem parâmetro retorna todas.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Editoras listadas com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Editora.class))))
    public ResponseEntity<List<Editora>> listar (@RequestParam(name = "ativa", required = false) Boolean ativa) {
        return ResponseEntity.ok(editoraService.listarPorAtiva(ativa));
    }

    @PatchMapping("/{id}/cidade-por-cep")
    @Operation(summary = "Atualizar cidade da editora por CEP", description = "Consulta uma API externa de CEP e atualiza a cidade da editora com o resultado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade da editora atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Editora.class))),
            @ApiResponse(responseCode = "400", description = "CEP inválido ou API externa sem cidade"),
            @ApiResponse(responseCode = "404", description = "Editora ou CEP não encontrado")})
    public ResponseEntity<Editora> atualizarCidadePorCep (
            @Parameter(description = "ID da editora", example = "1") @PathVariable Integer id,
            @Parameter(description = "CEP com 8 dígitos", example = "01001000") @RequestParam String cep) {
        return ResponseEntity.ok(editoraService.atualizarCidadePorCep(id, cep));
    }
}
