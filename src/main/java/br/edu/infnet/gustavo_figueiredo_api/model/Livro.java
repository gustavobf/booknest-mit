package br.edu.infnet.gustavo_figueiredo_api.model;

import java.util.ArrayList;
import java.util.List;

public class Livro implements Entidade {
    private Integer id;
    private String titulo;
    private String isbn;
    private Double preco;
    private Boolean disponivel;
    private Autor autor;
    private Categoria categoria;
    private Editora editora;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Livro() {}

    public Livro(Integer id, String titulo, String isbn, Double preco, Boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    public Livro(Integer id, String titulo, String isbn, Double preco, Boolean disponivel, Autor autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.preco = preco;
        this.disponivel = disponivel;
        this.autor = autor;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    @Override
    public void exibir() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", preco=" + preco +
                ", disponivel=" + disponivel +
                ", autor=" + (autor != null ? autor.getNome() : "N/A") +
                ", categoria=" + (categoria != null ? categoria.getNome() : "N/A") +
                ", editora=" + (editora != null ? editora.getNome() : "N/A") +
                ", emprestimos=" + emprestimos.size() +
                '}';
    }
}
