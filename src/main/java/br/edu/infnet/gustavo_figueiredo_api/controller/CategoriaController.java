package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias")
public class CategoriaController extends BaseCrudController<Categoria> {
    private final CategoriaService categoriaService;

    public CategoriaController (CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    protected BaseService<Categoria> getService () {
        return categoriaService;
    }

    @Override
    protected Integer getId (Categoria entidade) {
        return entidade.getId();
    }

    @Override
    protected void setId (Categoria entidade, Integer id) {
        entidade.setId(id);
    }

    @Override
    protected String getNomeEntidade () {
        return "Categoria";
    }

    @Override
    @PostMapping
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da categoria a ser criada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class), examples = @ExampleObject(name = "Categoria", value = "{\n  \"nome\": \"Fantasia\",\n  \"descricao\": \"Obras de fantasia épica e urbana\"\n}")))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<Categoria> incluir (@Valid @RequestBody Categoria entidade) {
        return super.incluir(entidade);
    }
}
