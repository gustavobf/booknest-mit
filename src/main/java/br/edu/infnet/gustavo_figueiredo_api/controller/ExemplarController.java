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

    @Override
    @PostMapping
    @Operation(summary = "Criar exemplar", description = "Cria um novo exemplar.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload do exemplar a ser criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exemplar.class), examples = @ExampleObject(name = "Exemplar", value = "{\n  \"codigo\": \"DOM-003\",\n  \"estadoConservacao\": \"BOM\",\n  \"disponivel\": true,\n  \"livro\": { \"id\": 1 }\n}")))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Exemplar criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<Exemplar> incluir (@Valid @RequestBody Exemplar entidade) {
        return super.incluir(entidade);
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exemplar", description = "Atualiza um exemplar existente pelo ID informado.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload atualizado do exemplar.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exemplar.class), examples = @ExampleObject(name = "Exemplar", value = "{\n  \"codigo\": \"DOM-001\",\n  \"estadoConservacao\": \"EXCELENTE\",\n  \"disponivel\": true,\n  \"livro\": { \"id\": 1 }\n}")))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Exemplar atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Exemplar não encontrado")})
    public ResponseEntity<Exemplar> alterar (@PathVariable Integer id, @Valid @RequestBody Exemplar entidade) {
        return super.alterar(id, entidade);
    }

    @Override
    @GetMapping
    @Operation(summary = "Listar exemplares", description = "Retorna exemplares com filtro opcional por disponibilidade. Sem parâmetro, retorna todos.")
    @Parameters({
            @Parameter(name = "disponivel", in = ParameterIn.QUERY, description = "Filtro opcional: true para disponíveis, false para indisponíveis. Sem parâmetro retorna todos.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Exemplares listados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exemplar.class))))
    public ResponseEntity<List<Exemplar>> listar (
            @RequestParam(name = "disponivel", required = false) Boolean disponivel) {
        return ResponseEntity.ok(exemplarService.listarPorDisponibilidade(disponivel));
    }

    @GetMapping("/livro/{idLivro}")
    @Operation(summary = "Listar exemplares por livro", description = "Retorna os exemplares associados a um livro específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exemplares do livro retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exemplar.class)))),
            @ApiResponse(responseCode = "400", description = "ID do livro inválido")})
    public ResponseEntity<List<Exemplar>> listarPorLivro (
            @Parameter(description = "ID do livro", example = "1") @PathVariable Integer idLivro) {
        return ResponseEntity.ok(exemplarService.listarPorLivro(idLivro));
    }
}
