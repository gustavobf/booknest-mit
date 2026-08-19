package br.edu.infnet.gustavo_figueiredo_api.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class Exemplar implements Entidade {
    @Schema(description = "ID do exemplar", example = "1")
    private Integer id;
    @Schema(description = "Código interno do exemplar", example = "DOM-001")
    private String codigo;
    @Schema(description = "Estado de conservação do exemplar", example = "BOM")
    private EstadoConservacao estadoConservacao;
    @Schema(description = "Disponibilidade atual do exemplar", example = "true")
    private Boolean disponivel;
    @Schema(description = "Livro associado ao exemplar", implementation = Livro.class)
    private Livro livro;
    @JsonIgnore
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Exemplar () {
    }

    public Exemplar (Integer id, String codigo, EstadoConservacao estadoConservacao, Boolean disponivel) {
        this.id = id;
        this.codigo = codigo;
        this.estadoConservacao = estadoConservacao;
        this.disponivel = disponivel;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    @Override
    public String getNome () {
        return codigo;
    }

    public String getCodigo () {
        return codigo;
    }

    public void setCodigo (String codigo) {
        this.codigo = codigo;
    }

    public EstadoConservacao getEstadoConservacao () {
        return estadoConservacao;
    }

    public void setEstadoConservacao (EstadoConservacao estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public Boolean getDisponivel () {
        return disponivel;
    }

    public void setDisponivel (Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Livro getLivro () {
        return livro;
    }

    public void setLivro (Livro livro) {
        this.livro = livro;
    }

    public List<Emprestimo> getEmprestimos () {
        return emprestimos;
    }

    public void adicionarEmprestimo (Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
        if (!emprestimo.estaDevolvido()) {
            disponivel = false;
        }
    }

    public void registrarDevolucao () {
        disponivel = true;
    }

    @Override
    public void exibir () {
        System.out.println(this);
    }

    @Override
    public String toString () {
        return "Exemplar{" + "id=" + id + ", codigo='" + codigo + '\'' + ", estadoConservacao=" + estadoConservacao + ", disponivel=" + disponivel + ", livro=" + (
                livro != null ? livro.getTitulo() : "N/A") + '}';
    }
}
