package org.example.hardwareshop;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private int id;
    private List<ItemPedido> itens;
    private double subtotal;
    private double desconto;
    private double total;
    private String cupomUtilizado;
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    private LocalDateTime dataCriacao;
    private String detalhePagamento;

    public Pedido(int id, List<ItemPedido> itens, double subtotal, double desconto, double total,
                  String cupomUtilizado, FormaPagamento formaPagamento, StatusPedido status,
                  LocalDateTime dataCriacao, String detalhePagamento) {
        this.id = id;
        this.itens = itens;
        this.subtotal = subtotal;
        this.desconto = desconto;
        this.total = total;
        this.cupomUtilizado = cupomUtilizado;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.detalhePagamento = detalhePagamento;
    }

    public int getId()                          { return id; }
    public List<ItemPedido> getItens()          { return itens; }
    public double getSubtotal()                 { return subtotal; }
    public double getDesconto()                 { return desconto; }
    public double getTotal()                    { return total; }
    public String getCupomUtilizado()           { return cupomUtilizado; }
    public FormaPagamento getFormaPagamento()   { return formaPagamento; }
    public StatusPedido getStatus()             { return status; }
    public LocalDateTime getDataCriacao()       { return dataCriacao; }
    public String getDetalhePagamento()         { return detalhePagamento; }

    public void setStatus(StatusPedido status) { this.status = status; }
}
