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
@RequestMapping("/autores")
@Tag(name = "Autores")
public class AutorController extends BaseCrudController<Autor> {
    private final AutorService autorService;

    public AutorController (AutorService autorService) {
        this.autorService = autorService;
    }

    @Override
    protected BaseService<Autor> getService () {
        return autorService;
    }

    @Override
    protected Integer getId (Autor entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Autor entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Autor";
    }

    @GetMapping("/nacionalidade/{nacionalidade}")
    @Operation(summary = "Buscar autores por nacionalidade", description = "Filtra autores pela nacionalidade informada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autores retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Autor.class)))),
            @ApiResponse(responseCode = "400", description = "Nacionalidade inválida")})
    public ResponseEntity<List<Autor>> buscarPorNacionalidade (
            @Parameter(description = "Nacionalidade a filtrar", example = "Brasileiro") @PathVariable String nacionalidade) {
        return ResponseEntity.ok(autorService.buscarPorNacionalidade(nacionalidade));
    }
}
