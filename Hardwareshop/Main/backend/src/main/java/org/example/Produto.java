package org.example.hardwareshop;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private int estoque;
    private String imagemUrl;
    private String especificacao;
    private CategoriaProduto categoria;

    public Produto() {
    }

    public Produto(int id, String nome, double preco, int estoque, String imagemUrl,
                    String especificacao, CategoriaProduto categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.imagemUrl = imagemUrl;
        this.especificacao = especificacao;
        this.categoria = categoria;
    }

    public int getId()               { return id; }
    public String getNome()          { return nome; }
    public double getPreco()         { return preco; }
    public int getEstoque()          { return estoque; }
    public String getImagemUrl()     { return imagemUrl; }
    public String getEspecificacao() { return especificacao; }
    public CategoriaProduto getCategoria() { return categoria; }

    // rótulo em português pronto para exibir no frontend (ex: "Placa de Vídeo")
    public String getCategoriaLabel() {
        return categoria != null ? categoria.getRotulo() : null;
    }

    public void setId(int id)                         { this.id = id; }
    public void setNome(String nome)                   { this.nome = nome; }
    public void setPreco(double preco)                 { this.preco = preco; }
    public void setImagemUrl(String imagemUrl)         { this.imagemUrl = imagemUrl; }
    public void setEspecificacao(String especificacao) { this.especificacao = especificacao; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }

    /**
     * Regra de negócio 1 - controle de estoque: retira "quantidade" unidades,
     * somente se houver disponibilidade suficiente.
     */
    public boolean diminuirEstoque(int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (estoque >= quantidade) {
            estoque -= quantidade;
            return true;
        }
        return false;
    }

    /**
     * Regra de negócio 1 - controle de estoque: reabastece o produto
     * (entrada de mercadoria / nova compra ao fornecedor).
     */
    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
        }
    }
}
