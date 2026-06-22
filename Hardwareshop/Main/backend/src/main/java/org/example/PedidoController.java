package org.example.hardwareshop;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // POST /pedidos → finaliza a compra: valida, processa pagamento e cria o pedido
    @PostMapping
    public Pedido finalizar(@RequestBody PagamentoRequest dto) {
        return pedidoService.finalizarPedido(dto);
    }

    // GET /pedidos → lista todos os pedidos
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    // GET /pedidos/{id} → retorna um pedido específico
    @GetMapping("/{id}")
    public Pedido obter(@PathVariable int id) {
        return pedidoService.buscarPorId(id);
    }

    // PUT /pedidos/{id}/status → atualiza o status (acompanhamento de entrega)
    @PutMapping("/{id}/status")
    public Pedido atualizarStatus(@PathVariable int id, @RequestBody StatusRequest dto) {
        return pedidoService.atualizarStatus(id, dto.getStatus());
    }
}
