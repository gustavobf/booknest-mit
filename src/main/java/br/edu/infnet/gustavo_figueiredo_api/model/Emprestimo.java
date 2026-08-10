package br.edu.infnet.gustavo_figueiredo_api.model;

import java.time.*;

public class Emprestimo implements Entidade {
    private Integer id;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataEsperadaDevolucao;
    private LocalDate dataDevolucao;
    private Double multa;
    private Exemplar exemplar;

    public Emprestimo () {
    }

    public Emprestimo (Integer id, LocalDate dataEmprestimo, LocalDate dataEsperadaDevolucao, LocalDate dataDevolucao,
                       Double multa) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataEsperadaDevolucao = dataEsperadaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.multa = multa;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    @Override
    public String getNome () {
        return usuario != null ? usuario.getNome() : "N/A";
    }

    public Usuario getUsuario () {
        return usuario;
    }

    public void setUsuario (Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataEmprestimo () {
        return dataEmprestimo;
    }

    public void setDataEmprestimo (LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataEsperadaDevolucao () {
        return dataEsperadaDevolucao;
    }

    public void setDataEsperadaDevolucao (LocalDate dataEsperadaDevolucao) {
        this.dataEsperadaDevolucao = dataEsperadaDevolucao;
    }

    public LocalDate getDataDevolucao () {
        return dataDevolucao;
    }

    public void setDataDevolucao (LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getMulta () {
        return multa;
    }

    public void setMulta (Double multa) {
        this.multa = multa;
    }

    public Exemplar getExemplar () {
        return exemplar;
    }

    public void setExemplar (Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public boolean estaDevolvido () {
        return dataDevolucao != null;
    }

    public boolean estaAtrasado () {
        if (dataEsperadaDevolucao == null) {
            return false;
        }

        LocalDate dataComparacao = estaDevolvido() ? dataDevolucao : LocalDate.now();
        return dataComparacao != null && dataComparacao.isAfter(dataEsperadaDevolucao);
    }

    public void registrarDevolucao (LocalDate dataDevolucao, Double multa) {
        this.dataDevolucao = dataDevolucao;
        this.multa = multa;
        if (exemplar != null) {
            exemplar.registrarDevolucao();
        }
    }

    @Override
    public void exibir () {
        System.out.println(this);
    }

    @Override
    public String toString () {
        return "Emprestimo{" + "id=" + id + ", usuario='" + (usuario != null ? usuario.getNome() :
                "N/A") + '\'' + ", dataEmprestimo=" + dataEmprestimo + ", dataEsperadaDevolucao=" + dataEsperadaDevolucao + ", dataDevolucao=" + dataDevolucao + ", multa=" + multa + ", exemplar=" + (
                exemplar != null ? exemplar.getCodigo() : "N/A") + ", livro=" + (
                exemplar != null && exemplar.getLivro() != null ? exemplar.getLivro().getTitulo() :
                        "N/A") + ", status='" + (estaDevolvido() ? "DEVOLVIDO" :
                "EM_ABERTO") + '\'' + ", atrasado=" + estaAtrasado() + '}';
    }
}
