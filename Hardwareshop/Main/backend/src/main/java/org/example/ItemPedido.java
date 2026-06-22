package org.example.hardwareshop;

public class ItemPedido {

    private int produtoId;
    private String nomeProduto;
    private double precoUnitario;
    private int quantidade;

    public ItemPedido(int produtoId, String nomeProduto, double precoUnitario, int quantidade) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public int getProdutoId()        { return produtoId; }
    public String getNomeProduto()   { return nomeProduto; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidade()       { return quantidade; }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }
}
