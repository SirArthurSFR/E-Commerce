package org.example.hardwareshop;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// isso aqui valida pedidos/pagamentos + status do pedido e entrega
@Service
public class PedidoService {

    // mapa de transições de status permitidas (de -> para)
    private static final Map<StatusPedido, Set<StatusPedido>> TRANSICOES_PERMITIDAS = Map.of(
            StatusPedido.AGUARDANDO_PAGAMENTO, Set.of(StatusPedido.PAGO, StatusPedido.CANCELADO),
            StatusPedido.PAGO, Set.of(StatusPedido.EM_PREPARACAO, StatusPedido.CANCELADO),
            StatusPedido.EM_PREPARACAO, Set.of(StatusPedido.ENVIADO, StatusPedido.CANCELADO),
            StatusPedido.ENVIADO, Set.of(StatusPedido.ENTREGUE),
            StatusPedido.ENTREGUE, Set.of(),
            StatusPedido.CANCELADO, Set.of()
    );

    private final ProdutoService produtoService;
    private final CarrinhoService carrinhoService;
    private final List<Pedido> pedidos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    public PedidoService(ProdutoService produtoService, CarrinhoService carrinhoService) {
        this.produtoService = produtoService;
        this.carrinhoService = carrinhoService;
    }

    // Finaliza a compra: valida carrinho, estoque e pagamento; só então cria o pedido
    public Pedido finalizarPedido(PagamentoRequest dto) {
        Carrinho carrinho = carrinhoService.obterCarrinho();

        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio. Adicione produtos antes de finalizar o pedido.");
        }

        // valida estoque de cada item antes de prosseguir com o pagamento
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = produtoService.buscarPorId(item.getProduto().getId());
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para " + produto.getNome() + ". Disponível: " + produto.getEstoque());
            }
        }

        FormaPagamento forma = converterFormaPagamento(dto.getFormaPagamento());
        Pagamento pagamento = criarPagamento(forma, dto);

        if (!pagamento.validar()) {
            throw new PagamentoInvalidoException(
                    "Dados de pagamento inválidos. Verifique o número do cartão (16 dígitos), CVV (3 dígitos) e validade (MM/AA).");
        }

        double total = carrinho.getTotal();
        String detalhePagamento = pagamento.processar(total);

        // baixa definitiva de estoque + monta os itens "congelados" do pedido
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = produtoService.buscarPorId(item.getProduto().getId());
            produto.diminuirEstoque(item.getQuantidade());
            itensPedido.add(new ItemPedido(produto.getId(), produto.getNome(), produto.getPreco(), item.getQuantidade()));
        }

        String cupomUtilizado = carrinho.getCupomAplicado() != null ? carrinho.getCupomAplicado().getCodigo() : null;

        Pedido pedido = new Pedido(
                proximoId.getAndIncrement(),
                itensPedido,
                carrinho.getSubtotal(),
                carrinho.getDesconto(),
                total,
                cupomUtilizado,
                forma,
                StatusPedido.PAGO,
                LocalDateTime.now(),
                detalhePagamento
        );

        pedidos.add(pedido);
        carrinhoService.limparCarrinho();

        return pedido;
    }

    private FormaPagamento converterFormaPagamento(String valor) {
        if (valor == null) {
            throw new PagamentoInvalidoException("Informe a forma de pagamento.");
        }
        try {
            return FormaPagamento.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new PagamentoInvalidoException("Forma de pagamento inválida: " + valor);
        }
    }

    private Pagamento criarPagamento(FormaPagamento forma, PagamentoRequest dto) {
        if (forma == FormaPagamento.CARTAO_CREDITO) {
            return new CartaoCreditoPagamento(dto.getNumeroCartao(), dto.getCvv(), dto.getValidade());
        }
        return new PixPagamento();
    }

    public List<Pedido> listar() {
        return pedidos;
    }

    public Pedido buscarPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: id " + id));
    }

    // avança/atualiza o status do pedido (acompanhamento de entrega)
    public Pedido atualizarStatus(int id, String novoStatusStr) {
        Pedido pedido = buscarPorId(id);

        if (novoStatusStr == null) {
            throw new StatusInvalidoException("Informe o novo status do pedido.");
        }

        StatusPedido novoStatus;
        try {
            novoStatus = StatusPedido.valueOf(novoStatusStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusInvalidoException("Status inválido: " + novoStatusStr);
        }

        Set<StatusPedido> permitidos = TRANSICOES_PERMITIDAS.get(pedido.getStatus());
        if (permitidos == null || !permitidos.contains(novoStatus)) {
            throw new StatusInvalidoException(
                    "Não é possível mudar o status de " + pedido.getStatus() + " para " + novoStatus + ".");
        }

        pedido.setStatus(novoStatus);
        return pedido;
    }
}
