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
                "imagens/Memoria-kingston-fury-beast-16gb.webp", "DDR4 3200MHz", CategoriaProduto.MEMORIA_RAM);
        cadastrarInicial("Placa Mãe MSI B550 Gaming AM4 DDR4", 839.99, 8,
                "imagens/placa-mae-msi-b550.webp", "AM4 / DDR4", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("SSD Kingston NV3 1TB M.2 NVMe Gen4", 889.00, 2,
                "imagens/SSD Kingston NV3 1TB.jpg", "1TB NVMe", CategoriaProduto.SSD);
        cadastrarInicial("Mouse Gamer Redragon Griffin Pro 3", 590.00, 3,
                "imagens/Mouse gamer sem fio redragon griffin pro 3.png", "16000 DPI",
                CategoriaProduto.PERIFERICOS);
        cadastrarInicial("Processador AMD Ryzen 7 7800X3D", 1998.00, 15,
                "imagens/Processador AMD Ryzen 7 7800X3D.webp",
                "5.0GHz Max Turbo, Cache 104MB, AM5, 8 Núcleos, Vídeo Integrado", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Fonte Gamer Rise Mode Zeus", 189.99, 6, "imagens/Fonte Gamer Rise Mode Zeus 500W.webp",
                "500W, PFC Ativo, Preto", CategoriaProduto.FONTE);
        cadastrarInicial("Gabinete Gamer Liketec Cygnus Dark", 329.99, 23, "imagens/Gabinete Gamer Liketec Cygnus Dark.webp",
                "ATX, Mid Tower, Vidro Temperado, Preto", CategoriaProduto.GABINETE);
        cadastrarInicial("Water Cooler Rise Mode Gamer Black", 219.99, 7, "imagens/Water Cooler Rise Mode Gamer Black.webp",
                "RGB, 240mm, AMD/Intel, Preto", CategoriaProduto.WATER_COOLER);
        cadastrarInicial("Placa de Vídeo PowerColor RX 9060 XT Reaper AMD", 2899.99, 5, "imagens/Placa de Vídeo PowerColor RX 9060 XT Reaper AMD Radeon.webp",
                "16GB, GDDR6, 128bits, OpenGL 4.6, RDNA 4", CategoriaProduto.PLACA_DE_VIDEO);
        cadastrarInicial("Placa de Vídeo RX 7600 GAMING OC 8G AMD Radeon Gigabyte", 1629.99, 13, "imagens/Placa de Vídeo RX 7600 GAMING OC.webp",
                "8GB, GDDR6, 128bits, RGB ", CategoriaProduto.PLACA_DE_VIDEO);
        cadastrarInicial("Placa de Vídeo XFX Swift RX 9070 XT WHITE TRIPLE FAN GAMING EDITION", 4445.99, 3,"imagens/Placa de Vídeo XFX Swift RX 9070 XT WHITE.webp",
                "16GB, GDDR6, HDMI 3xDP, RDNA 4", CategoriaProduto.PLACA_DE_VIDEO);
        cadastrarInicial("Placa De Vídeo MSI GeForce RTX 5060 Ti Shadow", 2789.99, 8, "imagens/Placa De Vídeo MSI GeForce RTX 5060 Ti Shadow.webp",
                "Nvidia, 8GB, GDDR7, 128-Bit", CategoriaProduto.PLACA_DE_VIDEO);
        cadastrarInicial("Placa-Mãe Gigabyte B550M Aorus Elite Rev. 1.3,", 669.99, 14, "imagens/Placa-Mãe Gigabyte B550M Aorus Elite Rev. 1.3.webp",
                "AMD AM4, Micro ATX, DDR4, Preto", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Placa-Mãe MSI A520M-A PRO, AMD AM4", 359.99, 30, "imagens/Placa-Mãe MSI A520M-A PRO, AMD AM4.webp",
                "mATX, DDR4, Preto - A520M", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Placa-Mãe MSI MPG B550 Gaming Plus", 829.99, 15, "imagens/Placa-Mãe MSI MPG B550 Gaming Plus.webp",
                "AMD AM4, ATX, DDR4, Preto,", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Placa-Mãe ASUS TUF GAMING A520M-PLUS II", 545.99, 11, "imagens/Placa-Mãe ASUS TUF GAMING A520M-PLUS II.webp",
                "AMD AM4, mATX, DDR4, Preto", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Placa-Mãe Gigabyte A520M K V2", 359.99, 3, "imagens/Placa-Mãe ASUS TUF GAMING A520M-PLUS II.webp",
                "Rev. 1.0, AMD, Micro ATX, DDR4, Preto", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Placa-Mãe MSI B760M Gaming Plus", 759.99, 25, "imagens/Placa-Mãe MSI B760M Gaming Plus.webp",
                "Intel LGA 1700, M-ATX, DDR5, Wi-Fi, Preto", CategoriaProduto.PLACA_MAE);
        cadastrarInicial("Processador AMD Ryzen 7 7700X", 1649.99, 16, "imagens/Processador AMD Ryzen 7 7700X.webp",
                "5.4GHz Max Turbo, Cache 40MB, AM5, 8 Núcleos, Vídeo Integrado", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador AMD Ryzen 5 7600X", 1149.98, 23, "imagens/Processador AMD Ryzen 5 7600X.webp",
                "5.3GHz Max Turbo, Cache 38MB, AM5, 6 Núcleos, Vídeo Integrado", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador AMD Ryzen 7 9800X3D", 2499.00, 2, "imagens/Processador AMD Ryzen 7 9800X3D.webp",
                "Cache 8MB, 8 Núcleos, 16 Threads, AM5 ", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador AMD Ryzen 3 3200G", 499.99, 8, "imagens/Processador AMD Ryzen 3 3200G.webp",
                "3.6GHz (4GHz Max Turbo), Cache 4MB, Quad Core, 4 Threads, AM4", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador Intel Core i5-12400F", 899.90, 17, "imagens/Processador Intel Core i5-12400F.webp",
                " 2.5GHz (4.4GHz Max Turbo), Cache 18MB, LGA 1700", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador Intel Core I5 14400f", 1199.00, 6, "imagens/Processador Intel Core I5 14400f.webp",
                "3.5 Ghz (4.7 Ghz Turbo), Cache 20mb, 10 Núcleos, 16 Threads, Lga1700, S/ Vídeo Integrado", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Processador Intel Core i9-14900KF", 2899.99, 18, "imagens/Processador Intel Core i9-14900KF.webp",
                "6GHz Max Turbo, Cache 36MB, 24 Núcleos, 32 Threads, LGA1700", CategoriaProduto.PROCESSADOR);
        cadastrarInicial("Memória RAM Husky Impulse, 8GB", 409.99, 12, "imagens/Memória RAM Husky Impulse, 8GB.webp",
                "3200MHz, DDR4, CL22, Preto", CategoriaProduto.MEMORIA_RAM);
        cadastrarInicial("Memória RAM XPG Lancer, RGB, 32GB (2x16GB)", 3499.99, 3, "imagens/Memória RAM XPG Lancer, RGB, 32GB (2x16GB).webp",
                "6000MHz, DDR5, CL30, Preto", CategoriaProduto.MEMORIA_RAM);
        cadastrarInicial("Memória RAM XPG Lancer Blade, 16GB", 1799.99, 5, "imagens/Memória RAM XPG Lancer Blade, 16GB.webp",
                "RGB, DDR5, 6000MHz, Single Tray, CL48, Preto", CategoriaProduto.MEMORIA_RAM);
        cadastrarInicial("SSD, Nvme, Winmemory, 256gb", 354.99, 7, "imagens/SSD, Nvme, Winmemory, 256gb.webp",
                "M.2 2280, PCie 3.0, Leitura 2500mb/s, Gravacao 2100mb/s", CategoriaProduto.SSD);
        cadastrarInicial("SSD Kingston NV3, 500 GB", 789.99, 19, "imagens/SSD Kingston NV3, 500 GB.webp",
                " M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 5000 MB/s, Gravação: 3000 MB/s, Azul", CategoriaProduto.SSD);
        cadastrarInicial("SSD Rise Mode Gamer M.2 Z Series, 512GB", 569.99, 8, "imagens/SSD Rise Mode Gamer M.2 Z Series, 512GB.webp",
                " M.2, NVMe, Leitura: 3100MB/s e Gravação: 1800MB/s", CategoriaProduto.SSD);
        cadastrarInicial("Fonte Gamer Atx Fortrek Black Hawk 650w", 322.15, 4, "imagens/Fonte Gamer Atx Fortrek Black Hawk 650w.webp",
                " 650w 80 Plus Bronze Pfc Ativo", CategoriaProduto.FONTE);
        cadastrarInicial("Fonte Gamer Atx Aerocool Stealth Vx-600 600w", 233.75, 6, "imagens/Fonte Gamer Atx Aerocool Stealth Vx-600 600w.webp",
                "600w Bivolt", CategoriaProduto.FONTE);
        cadastrarInicial("Fonte Gamer C3tech, 700W", 288.15,14, "imagens/Fonte Gamer C3tech, 700W.webp",
                "80 Plus Bronze, Pfc Ativo, ATX", CategoriaProduto.FONTE);
        cadastrarInicial("Fonte Cooler Master MWE Gold 850 V3, 850W", 359.99, 20, "imagens/Fonte Cooler Master MWE Gold 850 V3, 850W.webp",
        "80 Plus Ouro, ATX 3.1, PFC Ativo, Preto", CategoriaProduto.FONTE);
        cadastrarInicial("Gabinete Gamer Rise Mode Galaxy Glass M Mini", 149.99, 15, "imagens/Gabinete Gamer Rise Mode Galaxy Glass M Mini preto.webp",
                "M-ATX, Lateral e Frontal em Vidro Temperado, Sem FANs, Preto", CategoriaProduto.GABINETE);
        cadastrarInicial("Gabinete Gamer Rise Mode Glass 06X", 99.99, 6, "imagens/Gabinete Gamer Rise Mode Glass 06X.webp",
                "Mid Tower, ATX, Lateral em Vidro Fumê e Frontal em Vidro Temperado, Sem FANs, Preto",  CategoriaProduto.GABINETE);
        cadastrarInicial("Gabinete Gamer Rise Mode Galaxy Glass M Mini branco", 149.99, 9, "imagens/Gabinete Gamer Rise Mode Galaxy Glass M Mini branco.webp",
                "M-ATX, Lateral e Frontal em Vidro Temperado, Sem FANs, Branco", CategoriaProduto.GABINETE);
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
