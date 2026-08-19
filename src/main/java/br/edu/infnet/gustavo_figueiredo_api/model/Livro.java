package br.edu.infnet.gustavo_figueiredo_api.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class Livro implements Entidade {
    @Schema(description = "ID do livro", example = "1")
    private Integer id;
    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String titulo;
    @Schema(description = "ISBN do livro", example = "978-8535905571")
    private String isbn;
    @Schema(description = "Autor associado ao livro", implementation = Autor.class)
    private Autor autor;
    @Schema(description = "Categoria associada ao livro", implementation = Categoria.class)
    private Categoria categoria;
    @Schema(description = "Editora associada ao livro", implementation = Editora.class)
    private Editora editora;
    @JsonIgnore
    private final List<Exemplar> exemplares = new ArrayList<>();
    @JsonIgnore
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Livro () {
    }

    public Livro (Integer id, String titulo, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    @Override
    public String getNome () {
        return titulo;
    }

    public String getTitulo () {
        return titulo;
    }

    public void setTitulo (String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn () {
        return isbn;
    }

    public void setIsbn (String isbn) {
        this.isbn = isbn;
    }

    public Boolean getDisponivel () {
        return exemplares.stream().anyMatch(Exemplar::getDisponivel);
    }

    public Autor getAutor () {
        return autor;
    }

    public void setAutor (Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria () {
        return categoria;
    }

    public void setCategoria (Categoria categoria) {
        this.categoria = categoria;
    }

    public Editora getEditora () {
        return editora;
    }

    public void setEditora (Editora editora) {
        this.editora = editora;
    }

    public List<Exemplar> getExemplares () {
        return exemplares;
    }

    public void adicionarExemplar (Exemplar exemplar) {
        exemplares.add(exemplar);
    }

    public long getQuantidadeExemplaresDisponiveis () {
        return exemplares.stream().filter(Exemplar::getDisponivel).count();
    }

    public long getQuantidadeExemplaresEmprestados () {
        return exemplares.size() - getQuantidadeExemplaresDisponiveis();
    }

    public List<Emprestimo> getEmprestimos () {
        return emprestimos;
    }

    public void adicionarEmprestimo (Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    @Override
    public void exibir () {
        System.out.println(this);
    }

    @Override
    public String toString () {
        return "Livro{" + "id=" + id + ", titulo='" + titulo + '\'' + ", isbn='" + isbn + '\'' + ", autor=" + (
                autor != null ? autor.getNome() : "N/A") + ", categoria=" + (categoria != null ? categoria.getNome() :
                "N/A") + ", editora=" + (editora != null ? editora.getNome() :
                "N/A") + ", totalExemplares=" + exemplares.size() + ", exemplaresDisponiveis=" + getQuantidadeExemplaresDisponiveis() + ", exemplaresEmprestados=" + getQuantidadeExemplaresEmprestados() + '}';
    }
}
