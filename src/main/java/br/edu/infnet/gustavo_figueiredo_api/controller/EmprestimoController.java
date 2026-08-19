package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.controller.dto.*;
import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Override
    @GetMapping
    @Operation(summary = "Listar empréstimos", description = "Retorna empréstimos com filtro opcional por situação de atraso. Sem parâmetro, retorna todos.")
    @Parameters({
            @Parameter(name = "atrasado", in = ParameterIn.QUERY, description = "Filtro opcional: true para atrasados, false para não atrasados. Sem parâmetro retorna todos.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Empréstimos retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Emprestimo.class))))
    public ResponseEntity<List<Emprestimo>> listar (
            @RequestParam(name = "atrasado", required = false) Boolean atrasado) {
        return ResponseEntity.ok(emprestimoService.listarPorSituacaoAtraso(atrasado));
    }

    @PatchMapping("/{id}/devolucao")
    @Operation(summary = "Registrar devolução", description = "Registra a devolução de um empréstimo existente, atualizando data de devolução e multa.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Dados da devolução a ser registrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrarDevolucaoRequest.class), examples = @ExampleObject(value = "{\n  \"dataDevolucao\": \"2026-08-18\",\n  \"multa\": 2.5\n}")))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Emprestimo.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou empréstimo já devolvido"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")})
    public ResponseEntity<Emprestimo> registrarDevolucao (
            @Parameter(description = "ID do empréstimo", example = "2") @PathVariable Integer id,
            @RequestBody RegistrarDevolucaoRequest request) {
        if (request == null) {
            throw new DadosInvalidosException("Payload de devolução não pode ser nulo.");
        }
        return ResponseEntity.ok(emprestimoService.registrarDevolucao(id, request.dataDevolucao(), request.multa()));
    }
}
