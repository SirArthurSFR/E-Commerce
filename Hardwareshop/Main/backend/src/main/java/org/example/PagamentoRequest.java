package org.example.hardwareshop;

// isso aqui finaliza o pedido 👍
public class PagamentoRequest {

    private String formaPagamento; // "CARTAO_CREDITO" ou "PIX"
    private String numeroCartao;
    private String cvv;
    private String validade;

    public String getFormaPagamento() { return formaPagamento; }
    public String getNumeroCartao()   { return numeroCartao; }
    public String getCvv()            { return cvv; }
    public String getValidade()       { return validade; }

    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public void setNumeroCartao(String numeroCartao)     { this.numeroCartao = numeroCartao; }
    public void setCvv(String cvv)                       { this.cvv = cvv; }
    public void setValidade(String validade)             { this.validade = validade; }
}
