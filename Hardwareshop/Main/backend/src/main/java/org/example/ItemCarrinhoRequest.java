package org.example.hardwareshop;

// adiciona um item ao carrinho
public class ItemCarrinhoRequest {

    private int produtoId;
    private int quantidade;

    public int getProdutoId()  { return produtoId; }
    public int getQuantidade() { return quantidade; }

    public void setProdutoId(int produtoId)   { this.produtoId = produtoId; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
