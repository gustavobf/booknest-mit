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

    @Override
    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna categorias, com ordenação opcional por quantidade de livros.")
    @Parameters({
            @Parameter(name = "ordenarPorQuantidadeLivros", in = ParameterIn.QUERY, description = "Quando true, retorna categorias ordenadas da maior para a menor quantidade de livros.", example = "true")})
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Categoria.class))))
    public ResponseEntity<List<Categoria>> listar (
            @RequestParam(name = "ordenarPorQuantidadeLivros", required = false) Boolean ordenarPorQuantidadeLivros) {
        if (Boolean.TRUE.equals(ordenarPorQuantidadeLivros)) {
            return ResponseEntity.ok(categoriaService.listarOrdenadasPorQuantidadeLivros());
        }
        return ResponseEntity.ok(categoriaService.obterLista());
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria existente pelo ID informado.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload atualizado da categoria.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class), examples = @ExampleObject(name = "Categoria", value = "{\n  \"nome\": \"Ficção\",\n  \"descricao\": \"Narrativas de ficção literária (revisada)\"\n}")))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")})
    public ResponseEntity<Categoria> alterar (@PathVariable Integer id, @Valid @RequestBody Categoria entidade) {
        return super.alterar(id, entidade);
    }
}
