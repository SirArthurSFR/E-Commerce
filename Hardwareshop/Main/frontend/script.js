const API = "http://localhost:8080";

// ===================== CATEGORIAS (para o filtro) =====================
// Precisa bater exatamente com os valores do enum CategoriaProduto no backend.
const CATEGORIAS = [
    { valor: "PLACA_DE_VIDEO", rotulo: "Placa de Vídeo" },
    { valor: "PLACA_MAE",      rotulo: "Placa-Mãe" },
    { valor: "PROCESSADOR",    rotulo: "Processador" },
    { valor: "MEMORIA_RAM",    rotulo: "Memória RAM" },
    { valor: "SSD",            rotulo: "SSD" },
    { valor: "HD",             rotulo: "HD" },
    { valor: "FONTE",          rotulo: "Fonte" },
    { valor: "GABINETE",       rotulo: "Gabinete" },
    { valor: "COOLER",         rotulo: "Cooler" },
    { valor: "WATER_COOLER",   rotulo: "Water Cooler" },
    { valor: "VENTOINHA",      rotulo: "Ventoinha" },
    { valor: "MONITOR",        rotulo: "Monitor" },
    { valor: "NOTEBOOK",       rotulo: "Notebook" },
    { valor: "PERIFERICOS",    rotulo: "Periféricos" },
    { valor: "ACESSORIOS",     rotulo: "Acessórios" }
];

let filtroCategoriaAtual = "";
let termoBuscaAtual = "";

// ===================== PRODUTOS / VITRINE =====================

function renderizarFiltrosCategoria() {
    const container = document.getElementById("filtros-categoria");
    if (!container) return;

    let html = `<button type="button" class="filtro-botao ativo" data-valor="" onclick="selecionarCategoria('')">Todos</button>`;
    for (const cat of CATEGORIAS) {
        html += `<button type="button" class="filtro-botao" data-valor="${cat.valor}" onclick="selecionarCategoria('${cat.valor}')">${cat.rotulo}</button>`;
    }
    container.innerHTML = html;
}

function selecionarCategoria(valor) {
    filtroCategoriaAtual = valor;

    document.querySelectorAll(".filtro-botao").forEach(botao => {
        botao.classList.toggle("ativo", botao.dataset.valor === valor);
    });

    carregarProdutos();
}

function executarBusca() {
    const input = document.getElementById("input-busca");
    termoBuscaAtual = input ? input.value.trim() : "";
    carregarProdutos();
}

async function carregarProdutos() {
    const vitrine = document.getElementById("vitrine");
    if (!vitrine) return;

    const parametros = new URLSearchParams();
    if (termoBuscaAtual) parametros.set("nome", termoBuscaAtual);
    if (filtroCategoriaAtual) parametros.set("categoria", filtroCategoriaAtual);

    try {
        const resposta = await fetch(`${API}/produtos?${parametros.toString()}`);
        const produtos = await resposta.json();

        vitrine.innerHTML = "";

        if (produtos.length === 0) {
            vitrine.innerHTML = "<p>Nenhum produto encontrado para esse filtro/busca.</p>";
            return;
        }

        for (const produto of produtos) {
            vitrine.innerHTML += criarCardProduto(produto);
        }
    } catch (erro) {
        vitrine.innerHTML = "<p>Não foi possível carregar os produtos. Verifique se o servidor (Spring Boot) está rodando em localhost:8080.</p>";
    }
}

function criarCardProduto(produto) {
    const corEstoque = produto.estoque <= 2 ? "color:red" : "color:green";
    const semEstoque = produto.estoque === 0;

    return `
        <div class="produto-card" id="card-${produto.id}">
            ${produto.categoriaLabel ? `<span class="categoria-badge">${produto.categoriaLabel}</span>` : ""}
            <img src="${produto.imagemUrl}" alt="${produto.nome}" onerror="this.src=''">
            <h3>${produto.nome}</h3>
            <small>${produto.especificacao}</small>
            <h5 class="estoque" style="${corEstoque}">
                Estoque: ${produto.estoque} un
            </h5>
            <p class="preco">R$ ${produto.preco.toFixed(2).replace(".", ",")}</p>
            <button class="btn-comprar"
                    onclick="adicionarAoCarrinho(${produto.id})"
                    ${semEstoque ? "disabled" : ""}>
                ${semEstoque ? "Sem estoque" : "Adicionar ao Carrinho"}
            </button>
        </div>
    `;
}

// ===================== AUTENTICAÇÃO (login/cadastro temporário, sem banco) =====================

function obterSessao() {
    const bruto = localStorage.getItem("hardforce_sessao");
    return bruto ? JSON.parse(bruto) : null;
}

function salvarSessao(sessao) {
    localStorage.setItem("hardforce_sessao", JSON.stringify(sessao));
}

function limparSessao() {
    localStorage.removeItem("hardforce_sessao");
}

function atualizarNavAuth() {
    const elemento = document.getElementById("nav-auth");
    if (!elemento) return;

    const sessao = obterSessao();
    if (sessao && sessao.usuario) {
        const primeiroNome = sessao.usuario.nome.split(" ")[0];
        elemento.innerHTML = `
            <span class="saudacao">Olá, ${primeiroNome}</span>
            <a href="#" onclick="fazerLogout(); return false;">Sair</a>
        `;
    } else {
        elemento.innerHTML = `<a href="login.html">Login / Cadastro</a>`;
    }
}

async function fazerLogout() {
    const sessao = obterSessao();
    if (sessao && sessao.token) {
        try {
            await fetch(`${API}/usuarios/logout`, {
                method: "POST",
                headers: { "Authorization": `Bearer ${sessao.token}` }
            });
        } catch (erro) {
            // se o servidor estiver fora do ar, mesmo assim limpamos a sessão local
        }
    }
    limparSessao();
    atualizarNavAuth();
}

// ===================== CARRINHO =====================

async function adicionarAoCarrinho(produtoId) {
    try {
        const resposta = await fetch(`${API}/carrinho/itens`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ produtoId: produtoId, quantidade: 1 })
        });

        const dados = await resposta.json();

        if (!resposta.ok) {
            alert(dados.erro || "Não foi possível adicionar o produto ao carrinho.");
            return;
        }

        await atualizarContadorCarrinho();
    } catch (erro) {
        alert("Erro ao adicionar produto ao carrinho.");
    }
}

async function atualizarContadorCarrinho() {
    const contador = document.getElementById("contador-carrinho");
    if (!contador) return;

    try {
        const resposta = await fetch(`${API}/carrinho`);
        const carrinho = await resposta.json();
        const totalItens = carrinho.itens.reduce((soma, item) => soma + item.quantidade, 0);
        contador.textContent = totalItens;
    } catch (erro) {
        contador.textContent = "0";
    }
}

async function abrirCarrinho() {
    const modal = document.getElementById("carrinho-modal");
    if (!modal) return;
    modal.classList.remove("hidden");
    await renderizarCarrinho();
}

function fecharCarrinho() {
    document.getElementById("carrinho-modal").classList.add("hidden");
}

async function renderizarCarrinho() {
    const corpo = document.getElementById("carrinho-corpo");
    const resposta = await fetch(`${API}/carrinho`);
    const carrinho = await resposta.json();

    const btnFinalizar = document.getElementById("btn-finalizar");

    if (carrinho.itens.length === 0) {
        corpo.innerHTML = "<p>Seu carrinho está vazio.</p>";
        document.getElementById("carrinho-resumo").innerHTML = "";
        if (btnFinalizar) btnFinalizar.disabled = true;
        await atualizarContadorCarrinho();
        return;
    }

    if (btnFinalizar) btnFinalizar.disabled = false;

    let html = "";
    for (const item of carrinho.itens) {
        html += `
            <div class="carrinho-item">
                <span class="carrinho-item-nome">${item.produto.nome}</span>
                <div class="carrinho-item-qtd">
                    <button onclick="alterarQuantidade(${item.produto.id}, ${item.quantidade - 1})">-</button>
                    <span>${item.quantidade}</span>
                    <button onclick="alterarQuantidade(${item.produto.id}, ${item.quantidade + 1})">+</button>
                </div>
                <span class="carrinho-item-preco">R$ ${item.subtotal.toFixed(2).replace(".", ",")}</span>
                <button class="btn-remover" onclick="removerDoCarrinho(${item.produto.id})">✕</button>
            </div>
        `;
    }
    corpo.innerHTML = html;

    const cupomTexto = carrinho.cupomAplicado
        ? `Cupom aplicado: <strong>${carrinho.cupomAplicado.codigo}</strong> (-${carrinho.cupomAplicado.percentualDesconto}%)
           <button class="btn-link" onclick="removerCupom()">remover</button>`
        : "Nenhum cupom aplicado.";

    document.getElementById("carrinho-resumo").innerHTML = `
        <p>Subtotal: R$ ${carrinho.subtotal.toFixed(2).replace(".", ",")}</p>
        <p>Desconto: - R$ ${carrinho.desconto.toFixed(2).replace(".", ",")}</p>
        <p class="carrinho-total">Total: R$ ${carrinho.total.toFixed(2).replace(".", ",")}</p>
        <p class="cupom-info">${cupomTexto}</p>
    `;

    await atualizarContadorCarrinho();
}

async function alterarQuantidade(produtoId, novaQuantidade) {
    try {
        const resposta = await fetch(`${API}/carrinho/itens/${produtoId}?quantidade=${novaQuantidade}`, {
            method: "PUT"
        });
        const dados = await resposta.json();
        if (!resposta.ok) {
            alert(dados.erro || "Não foi possível atualizar a quantidade.");
        }
        await renderizarCarrinho();
    } catch (erro) {
        alert("Erro ao atualizar quantidade.");
    }
}

async function removerDoCarrinho(produtoId) {
    await fetch(`${API}/carrinho/itens/${produtoId}`, { method: "DELETE" });
    await renderizarCarrinho();
}

async function aplicarCupom() {
    const input = document.getElementById("input-cupom");
    const codigo = input.value;

    const resposta = await fetch(`${API}/carrinho/cupom`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ codigo: codigo })
    });
    const dados = await resposta.json();

    if (!resposta.ok) {
        alert(dados.erro || "Cupom inválido.");
        return;
    }

    input.value = "";
    await renderizarCarrinho();
}

async function removerCupom() {
    await fetch(`${API}/carrinho/cupom`, { method: "DELETE" });
    await renderizarCarrinho();
}

// ===================== CHECKOUT / PAGAMENTO =====================

function abrirPagamento() {
    document.getElementById("carrinho-modal").classList.add("hidden");
    document.getElementById("pagamento-modal").classList.remove("hidden");
    alternarCamposPagamento();
}

function fecharPagamento() {
    document.getElementById("pagamento-modal").classList.add("hidden");
}

function alternarCamposPagamento() {
    const selecionado = document.querySelector('input[name="forma-pagamento"]:checked');
    const forma = selecionado ? selecionado.value : "CARTAO_CREDITO";
    document.getElementById("campos-cartao").classList.toggle("hidden", forma !== "CARTAO_CREDITO");
}

async function confirmarPagamento(event) {
    event.preventDefault();

    const selecionado = document.querySelector('input[name="forma-pagamento"]:checked');
    const forma = selecionado ? selecionado.value : "CARTAO_CREDITO";

    const corpo = {
        formaPagamento: forma,
        numeroCartao: document.getElementById("cartao-numero").value,
        cvv: document.getElementById("cartao-cvv").value,
        validade: document.getElementById("cartao-validade").value
    };

    try {
        const resposta = await fetch(`${API}/pedidos`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(corpo)
        });

        const pedido = await resposta.json();

        if (!resposta.ok) {
            alert(pedido.erro || "Não foi possível concluir o pedido.");
            return;
        }

        fecharPagamento();
        mostrarConfirmacao(pedido);
        await carregarProdutos();
        await atualizarContadorCarrinho();
        await carregarPedidos();
    } catch (erro) {
        alert("Erro ao processar pagamento.");
    }
}

function mostrarConfirmacao(pedido) {
    const modal = document.getElementById("confirmacao-modal");
    document.getElementById("confirmacao-corpo").innerHTML = `
        <p>Pedido <strong>#${pedido.id}</strong> confirmado!</p>
        <p>Total pago: R$ ${pedido.total.toFixed(2).replace(".", ",")}</p>
        <p>Status: <span class="status-badge status-${pedido.status}">${traduzirStatus(pedido.status)}</span></p>
        <p>${pedido.detalhePagamento}</p>
        <p><a href="pedidos.html">Acompanhar pedido →</a></p>
    `;
    modal.classList.remove("hidden");
}

function fecharConfirmacao() {
    document.getElementById("confirmacao-modal").classList.add("hidden");
}

// ===================== PEDIDOS / STATUS DE ENTREGA =====================

function traduzirStatus(status) {
    const mapa = {
        AGUARDANDO_PAGAMENTO: "Aguardando pagamento",
        PAGO: "Pago",
        EM_PREPARACAO: "Em preparação",
        ENVIADO: "Enviado",
        ENTREGUE: "Entregue",
        CANCELADO: "Cancelado"
    };
    return mapa[status] || status;
}

const PROXIMO_STATUS = {
    AGUARDANDO_PAGAMENTO: "PAGO",
    PAGO: "EM_PREPARACAO",
    EM_PREPARACAO: "ENVIADO",
    ENVIADO: "ENTREGUE"
};

async function carregarPedidos() {
    const lista = document.getElementById("lista-pedidos");
    if (!lista) return;

    try {
        const resposta = await fetch(`${API}/pedidos`);
        const pedidos = await resposta.json();

        if (pedidos.length === 0) {
            lista.innerHTML = "<p>Nenhum pedido foi realizado ainda.</p>";
            return;
        }

        let html = "";
        for (const pedido of [...pedidos].reverse()) {
            const proximo = PROXIMO_STATUS[pedido.status];
            const itensHtml = pedido.itens
                .map(i => `<li>${i.quantidade}x ${i.nomeProduto} — R$ ${i.subtotal.toFixed(2).replace(".", ",")}</li>`)
                .join("");

            html += `
                <div class="pedido-card">
                    <div class="pedido-cabecalho">
                        <h3>Pedido #${pedido.id}</h3>
                        <span class="status-badge status-${pedido.status}">${traduzirStatus(pedido.status)}</span>
                    </div>
                    <ul>${itensHtml}</ul>
                    <p>Forma de pagamento: ${pedido.formaPagamento === "PIX" ? "Pix" : "Cartão de Crédito"}</p>
                    ${pedido.cupomUtilizado ? `<p>Cupom utilizado: ${pedido.cupomUtilizado}</p>` : ""}
                    <p class="pedido-total">Total: R$ ${pedido.total.toFixed(2).replace(".", ",")}</p>
                    <div class="pedido-acoes">
                        ${proximo ? `<button onclick="avancarStatus(${pedido.id}, '${proximo}')">Avançar para "${traduzirStatus(proximo)}"</button>` : ""}
                        ${pedido.status !== "ENTREGUE" && pedido.status !== "CANCELADO" ? `<button class="btn-cancelar" onclick="avancarStatus(${pedido.id}, 'CANCELADO')">Cancelar pedido</button>` : ""}
                    </div>
                </div>
            `;
        }
        lista.innerHTML = html;
    } catch (erro) {
        lista.innerHTML = "<p>Não foi possível carregar os pedidos.</p>";
    }
}

async function avancarStatus(pedidoId, novoStatus) {
    try {
        const resposta = await fetch(`${API}/pedidos/${pedidoId}/status`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ status: novoStatus })
        });
        const dados = await resposta.json();
        if (!resposta.ok) {
            alert(dados.erro || "Não foi possível atualizar o status.");
            return;
        }
        await carregarPedidos();
    } catch (erro) {
        alert("Erro ao atualizar status do pedido.");
    }
}

// ===================== INICIALIZAÇÃO =====================

document.addEventListener("DOMContentLoaded", () => {
    renderizarFiltrosCategoria();
    carregarProdutos();
    atualizarContadorCarrinho();
    carregarPedidos();
    atualizarNavAuth();
});
