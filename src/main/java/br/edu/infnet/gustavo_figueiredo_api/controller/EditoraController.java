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
    @GetMapping
    @Operation(summary = "Listar editoras", description = "Retorna editoras com filtro opcional por status de atividade. Sem parâmetro, retorna todas.")
    @Parameters({
            @Parameter(name = "ativa", in = ParameterIn.QUERY, description = "Filtro opcional: true para ativas, false para inativas. Sem parâmetro retorna todas.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Editoras listadas com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Editora.class))))
    public ResponseEntity<List<Editora>> listar (@RequestParam(name = "ativa", required = false) Boolean ativa) {
        return ResponseEntity.ok(editoraService.listarPorAtiva(ativa));
    }
}
