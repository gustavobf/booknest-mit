package br.edu.infnet.gustavo_figueiredo_api.model;

import java.time.LocalDate;

public class Emprestimo implements Entidade {
    private Integer id;
    private String nomeUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataEsperadaDevolucao;
    private LocalDate dataDevolucao;
    private Double multa;
    private Livro livro;

    public Emprestimo() {}

    public Emprestimo(Integer id, String nomeUsuario, LocalDate dataEmprestimo, LocalDate dataEsperadaDevolucao, LocalDate dataDevolucao, Double multa) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataEsperadaDevolucao = dataEsperadaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.multa = multa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nomeUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataEsperadaDevolucao() {
        return dataEsperadaDevolucao;
    }

    public void setDataEsperadaDevolucao(LocalDate dataEsperadaDevolucao) {
        this.dataEsperadaDevolucao = dataEsperadaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public void exibir() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataEsperadaDevolucao=" + dataEsperadaDevolucao +
                ", dataDevolucao=" + dataDevolucao +
                ", multa=" + multa +
                ", livro=" + (livro != null ? livro.getTitulo() : "N/A") +
                '}';
    }
}
