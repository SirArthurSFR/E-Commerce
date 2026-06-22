package org.example.hardwareshop;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinho")
@CrossOrigin("*")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    // GET /carrinho → retorna o carrinho atual
    @GetMapping
    public Carrinho obter() {
        return carrinhoService.obterCarrinho();
    }

    // POST /carrinho/itens → adiciona um produto ao carrinho
    @PostMapping("/itens")
    public Carrinho adicionar(@RequestBody ItemCarrinhoRequest dto) {
        return carrinhoService.adicionarItem(dto.getProdutoId(), dto.getQuantidade());
    }

    // PUT /carrinho/itens/{produtoId}?quantidade=3 → altera a quantidade de um item
    @PutMapping("/itens/{produtoId}")
    public Carrinho atualizar(@PathVariable int produtoId, @RequestParam int quantidade) {
        return carrinhoService.atualizarQuantidade(produtoId, quantidade);
    }

    // DELETE /carrinho/itens/{produtoId} → remove um item do carrinho
    @DeleteMapping("/itens/{produtoId}")
    public Carrinho remover(@PathVariable int produtoId) {
        return carrinhoService.removerItem(produtoId);
    }

    // POST /carrinho/cupom → aplica um cupom de desconto
    @PostMapping("/cupom")
    public Carrinho aplicarCupom(@RequestBody CupomRequest dto) {
        return carrinhoService.aplicarCupom(dto.getCodigo());
    }

    // DELETE /carrinho/cupom → remove o cupom aplicado
    @DeleteMapping("/cupom")
    public Carrinho removerCupom() {
        return carrinhoService.removerCupom();
    }

    // DELETE /carrinho → limpa o carrinho inteiro
    @DeleteMapping
    public Carrinho limpar() {
        return carrinhoService.limparCarrinho();
    }
}
