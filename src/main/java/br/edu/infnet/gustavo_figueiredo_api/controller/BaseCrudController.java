package br.edu.infnet.gustavo_figueiredo_api.controller;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import java.net.*;
import java.util.*;

public abstract class BaseCrudController<T extends Entidade> {

    protected abstract BaseService<T> getService ();

    protected abstract Integer getId (T entidade);

    protected abstract void setId (T entidade, Integer id);

    protected abstract String getNomeEntidade ();

    @GetMapping
    @Operation(summary = "Listar registros", description = "Retorna todos os registros do recurso.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Object.class))))
    public ResponseEntity<List<T>> listar (@RequestParam(required = false) Boolean filtro) {
        return ResponseEntity.ok(getService().obterLista());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro por ID", description = "Retorna um registro específico pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")})
    public ResponseEntity<T> obterPorId (@PathVariable Integer id) {
        return ResponseEntity.ok(getService().obterPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cria um novo registro para o recurso.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload do registro a ser criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class), examples = @ExampleObject(name = "Exemplo", value = "{ \"id\": 1 }")))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<T> incluir (@RequestBody T entidade) {
        T entidadeCriada = getService().incluir(validarEntidade(entidade));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(getId(entidadeCriada)).toUri();
        return ResponseEntity.created(location).body(entidadeCriada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro existente pelo ID informado.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload atualizado do registro.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class), examples = @ExampleObject(name = "Exemplo", value = "{ \"id\": 1 }")))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")})
    public ResponseEntity<T> alterar (@PathVariable Integer id, @RequestBody T entidade) {
        T entidadeValida = validarEntidade(entidade);
        setId(entidadeValida, id);
        return ResponseEntity.ok(getService().alterar(entidadeValida));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Remove um registro existente pelo ID informado.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Registro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")})
    public ResponseEntity<Void> excluir (@PathVariable Integer id) {
        getService().excluir(id);
        return ResponseEntity.noContent().build();
    }

    private T validarEntidade (T entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException(getNomeEntidade() + " não pode ser nulo.");
        }
        return entidade;
    }
}
