package org.example.hardwareshop;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();
    private Cupom cupomAplicado;

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    // essa parada aqui adiciona itens no carrinho
    public void adicionarItem(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, quantidade));
    }

    // e essa aqui remove itens
    public boolean removerItem(int produtoId) {
        return itens.removeIf(item -> item.getProduto().getId() == produtoId);
    }

    public boolean atualizarQuantidade(int produtoId, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == produtoId) {
                item.setQuantidade(quantidade);
                return true;
            }
        }
        return false;
    }

    public int quantidadeNoCarrinho(int produtoId) {
        return itens.stream()
                .filter(i -> i.getProduto().getId() == produtoId)
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
    }

    // isso aqui é pra calcular os cupons de desconto
    public double getSubtotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getDesconto() {
        if (cupomAplicado == null || !cupomAplicado.isValido()) {
            return 0;
        }
        return getSubtotal() * (cupomAplicado.getPercentualDesconto() / 100.0);
    }

    public double getTotal() {
        return getSubtotal() - getDesconto();
    }

    public Cupom getCupomAplicado()              { return cupomAplicado; }
    public void setCupomAplicado(Cupom cupom)    { this.cupomAplicado = cupom; }
    public void removerCupom()                   { this.cupomAplicado = null; }

    public void limpar() {
        itens.clear();
        cupomAplicado = null;
    }
}
