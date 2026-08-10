package br.edu.infnet.gustavo_figueiredo_api.model;

import java.util.*;

public class Autor implements Entidade {
    private Integer id;
    private String nome;
    private String nacionalidade;
    private Integer anoNascimento;
    private List<Livro> livros = new ArrayList<>();

    public Autor () {
    }

    public Autor (Integer id, String nome, String nacionalidade, Integer anoNascimento) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.anoNascimento = anoNascimento;
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

    public String getNacionalidade () {
        return nacionalidade;
    }

    public void setNacionalidade (String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getAnoNascimento () {
        return anoNascimento;
    }

    public void setAnoNascimento (Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
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
        return "Autor{" + "id=" + id + ", nome='" + nome + '\'' + ", nacionalidade='" + nacionalidade + '\'' + ", anoNascimento=" + anoNascimento + ", livros=" + livros.size() + '}';
    }
}
