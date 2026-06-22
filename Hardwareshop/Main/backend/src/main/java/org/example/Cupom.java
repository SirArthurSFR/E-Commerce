package org.example.hardwareshop;

public class Cupom {

    private String codigo;
    private double percentualDesconto;
    private boolean ativo;

    public Cupom(String codigo, double percentualDesconto, boolean ativo) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.ativo = ativo;
    }

    public String getCodigo()             { return codigo; }
    public double getPercentualDesconto() { return percentualDesconto; }
    public boolean isValido()             { return ativo; }
}
