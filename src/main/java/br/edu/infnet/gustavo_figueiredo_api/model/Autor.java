package br.edu.infnet.gustavo_figueiredo_api.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class Autor implements Entidade {
    @Schema(description = "ID do autor", example = "1")
    private Integer id;
    @Schema(description = "Nome completo do autor", example = "Machado de Assis")
    private String nome;
    @Schema(description = "Nacionalidade do autor", example = "Brasileiro")
    private String nacionalidade;
    @Schema(description = "Ano de nascimento do autor", example = "1839")
    private Integer anoNascimento;
    @JsonIgnore
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
