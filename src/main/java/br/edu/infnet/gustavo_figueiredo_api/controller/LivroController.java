package br.edu.infnet.gustavo_figueiredo_api.controller;

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

    @Override
    @GetMapping
    @Operation(summary = "Listar livros", description = "Retorna livros com filtro opcional por disponibilidade. Sem parâmetro, retorna todos.")
    @Parameters({
            @Parameter(name = "disponivel", in = ParameterIn.QUERY, description = "Filtro opcional: true para disponíveis, false para indisponíveis. Sem parâmetro retorna todos.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Livros listados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Livro.class))))
    public ResponseEntity<List<Livro>> listar (
            @RequestParam(name = "disponivel", required = false) Boolean disponivel) {
        return ResponseEntity.ok(livroService.listarPorDisponibilidade(disponivel));
    }
}
