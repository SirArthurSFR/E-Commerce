package org.example.hardwareshop;

import org.springframework.stereotype.Service;

// itens do carrinho + total com desconto/cupom
@Service
public class CarrinhoService {

    private final ProdutoService produtoService;
    private final CupomService cupomService;
    private final Carrinho carrinho = new Carrinho();

    public CarrinhoService(ProdutoService produtoService, CupomService cupomService) {
        this.produtoService = produtoService;
        this.cupomService = cupomService;
    }

    public Carrinho obterCarrinho() {
        return carrinho;
    }

    public Carrinho adicionarItem(int produtoId, int quantidade) {
        if (quantidade <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero.");
        }
        Produto produto = produtoService.buscarPorId(produtoId);
        int jaNoCarrinho = carrinho.quantidadeNoCarrinho(produtoId);

        if (produto.getEstoque() < jaNoCarrinho + quantidade) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para " + produto.getNome() + ". Disponível: " + produto.getEstoque());
        }
        carrinho.adicionarItem(produto, quantidade);
        return carrinho;
    }

    public Carrinho atualizarQuantidade(int produtoId, int quantidade) {
        if (quantidade <= 0) {
            carrinho.removerItem(produtoId);
            return carrinho;
        }
        Produto produto = produtoService.buscarPorId(produtoId);
        if (produto.getEstoque() < quantidade) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para " + produto.getNome() + ". Disponível: " + produto.getEstoque());
        }
        carrinho.atualizarQuantidade(produtoId, quantidade);
        return carrinho;
    }

    public Carrinho removerItem(int produtoId) {
        carrinho.removerItem(produtoId);
        return carrinho;
    }

    public Carrinho aplicarCupom(String codigo) {
        Cupom cupom = cupomService.validar(codigo);
        carrinho.setCupomAplicado(cupom);
        return carrinho;
    }

    public Carrinho removerCupom() {
        carrinho.removerCupom();
        return carrinho;
    }

    public Carrinho limparCarrinho() {
        carrinho.limpar();
        return carrinho;
    }
}
