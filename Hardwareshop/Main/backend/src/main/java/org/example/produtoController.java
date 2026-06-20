package org.example.hardwareshop;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class produtoController {

    private final ProdutoService produtoService;

    public produtoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // GET /produtos                                  → lista todos
    // GET /produtos?nome=mouse                        → busca por nome
    // GET /produtos?categoria=PLACA_DE_VIDEO          → filtra por categoria
    // GET /produtos?nome=gamer&categoria=MONITOR      → busca + filtro combinados
    @GetMapping
    public List<Produto> listar(@RequestParam(required = false) String nome,
                                  @RequestParam(required = false) String categoria) {
        return produtoService.listar(nome, categoria);
    }

    // GET /produtos/{id} → retorna um produto pelo id
    @GetMapping("/{id}")
    public Produto obter(@PathVariable int id) {
        return produtoService.buscarPorId(id);
    }

    // POST /produtos → cadastra um novo produto no catálogo
    @PostMapping
    public Produto cadastrar(@RequestBody ProdutoRequest dto) {
        return produtoService.cadastrar(dto);
    }

    // PUT /produtos/{id}/estoque?quantidade=10 → reabastece o estoque
    @PutMapping("/{id}/estoque")
    public Produto reabastecer(@PathVariable int id, @RequestParam int quantidade) {
        return produtoService.reabastecerEstoque(id, quantidade);
    }
}
