package br.edu.infnet.gustavo_figueiredo_api.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class Editora implements Entidade {
    @Schema(description = "ID da editora", example = "1")
    private Integer id;
    @Schema(description = "Nome da editora", example = "Companhia das Letras")
    private String nome;
    @Schema(description = "Cidade sede da editora", example = "São Paulo")
    private String cidade;
    @Schema(description = "E-mail de contato", example = "contato@companhiadasletras.com.br")
    private String emailContato;
    @Schema(description = "Indicador de atividade da editora", example = "true")
    private Boolean ativa;
    @JsonIgnore
    private List<Livro> livros = new ArrayList<>();

    public Editora () {
    }

    public Editora (Integer id, String nome, String cidade, String emailContato, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.emailContato = emailContato;
        this.ativa = ativa;
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

    public String getCidade () {
        return cidade;
    }

    public void setCidade (String cidade) {
        this.cidade = cidade;
    }

    public String getEmailContato () {
        return emailContato;
    }

    public void setEmailContato (String emailContato) {
        this.emailContato = emailContato;
    }

    public Boolean getAtiva () {
        return ativa;
    }

    public void setAtiva (Boolean ativa) {
        this.ativa = ativa;
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
        return "Editora{" + "id=" + id + ", nome='" + nome + '\'' + ", cidade='" + cidade + '\'' + ", emailContato='" + emailContato + '\'' + ", ativa=" + ativa + ", livros=" + livros.size() + '}';
    }
}
