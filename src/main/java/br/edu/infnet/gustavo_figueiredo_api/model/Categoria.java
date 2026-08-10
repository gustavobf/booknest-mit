package br.edu.infnet.gustavo_figueiredo_api.model;

import java.util.*;

public class Categoria implements Entidade {
    private Integer id;
    private String nome;
    private String descricao;
    private List<Livro> livros = new ArrayList<>();

    public Categoria () {
    }

    public Categoria (Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    @Override
    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getDescricao () {
        return descricao;
    }

    public void setDescricao (String descricao) {
        this.descricao = descricao;
    }

    public List<Livro> getLivros () {
        return livros;
    }

    public void adicionarLivro (Livro livro) {
        this.livros.add(livro);
    }

    @Override
    public void exibir () {
        System.out.println(this);
    }

    @Override
    public String toString () {
        return "Categoria{" + "id=" + id + ", nome='" + nome + '\'' + ", descricao='" + descricao + '\'' + ", livros=" + livros.size() + '}';
    }
}
