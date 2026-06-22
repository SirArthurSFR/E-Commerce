package org.example.hardwareshop;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// pra cadastrar produtos e controlar estoque
// + busca por nome e filtro por categoria (na memoria msm por enquanto, sem banco de dados)
@Service
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    // pra voces cadastrarem novos produtos, só chamar esse metodo cadastrarInicial, então colocar o produto nessa ordem:
    // nome > preço > estoque > url da imagem > especificações > categoria do produto

    public ProdutoService() {
        cadastrarInicial("GeForce RTX 5070 Ti GAMING OC 16G", 6899.90, 5,
                "imagens/RTX 5070 TI.png", "16GB VRAM", CategoriaProduto.PLACA_DE_VIDEO);
        cadastrarInicial("Memória Kingston Fury Beast 16GB", 799.99, 3,
                "imagens/memoria-kingston.webp", "DDR4 3200MHz", CategoriaProduto.MEMORIA_RAM);
        cadastrarInicial("Placa Mãe MSI B550 Gaming AM4 DDR4", 839.99, 8,
                "imagens/placa-mae-msi-b550.webp", "AM4 / DDR4", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("SSD Kingston NV3 1TB M.2 NVMe Gen4", 889.00, 2,
                "imagens/SSD Kingston NV3 1TB.jpg", "1TB NVMe", CategoriaProduto.SSD);
        cadastrarInicial("Mouse Gamer Redragon Griffin Pro 3", 590.00, 3,
                "imagens/Mouse gamer sem fio redragon griffin pro 3.png", "16000 DPI", CategoriaProduto.PERIFERICOS);
    }

    private void cadastrarInicial(String nome, double preco, int estoque, String imagemUrl,
                                    String especificacao, CategoriaProduto categoria) {
        produtos.add(new Produto(proximoId.getAndIncrement(), nome, preco, estoque, imagemUrl, especificacao, categoria));
    }

    public List<Produto> listar() {
        return produtos;
    }

    // busca por nome (contém, sem diferenciar maiúsc/minúsc)
    public List<Produto> listar(String nome, String categoriaStr) {
        return produtos.stream()
                .filter(p -> nome == null || nome.isBlank()
                        || p.getNome().toLowerCase().contains(nome.trim().toLowerCase()))
                .filter(p -> categoriaStr == null || categoriaStr.isBlank()
                        || p.getCategoria().name().equalsIgnoreCase(categoriaStr.trim()))
                .collect(Collectors.toList());
    }

    public Produto buscarPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: id " + id));
    }

    // cadastrar novo produto no catálogo
    public Produto cadastrar(ProdutoRequest dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RegraNegocioException("O nome do produto é obrigatório.");
        }
        if (dto.getPreco() <= 0) {
            throw new RegraNegocioException("O preço do produto deve ser maior que zero.");
        }
        if (dto.getEstoque() < 0) {
            throw new RegraNegocioException("O estoque inicial não pode ser negativo.");
        }

        CategoriaProduto categoria = converterCategoria(dto.getCategoria());

        Produto produto = new Produto(
                proximoId.getAndIncrement(),
                dto.getNome(),
                dto.getPreco(),
                dto.getEstoque(),
                dto.getImagemUrl(),
                dto.getEspecificacao(),
                categoria
        );
        produtos.add(produto);
        return produto;
    }

    private CategoriaProduto converterCategoria(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException("Informe a categoria do produto.");
        }
        try {
            return CategoriaProduto.valueOf(valor.trim().toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new RegraNegocioException("Categoria inválida: " + valor);
        }
    }

    // reabastecer estoque de um produto já cadastrado
    public Produto reabastecerEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new RegraNegocioException("A quantidade para reabastecimento deve ser maior que zero.");
        }
        Produto produto = buscarPorId(id);
        produto.aumentarEstoque(quantidade);
        return produto;
    }
}
