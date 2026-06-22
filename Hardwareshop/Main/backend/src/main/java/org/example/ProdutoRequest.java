package org.example.hardwareshop;

// Usado no cadastro de novos produtos (POST /produtos)
public class ProdutoRequest {

    private String nome;
    private double preco;
    private int estoque;
    private String imagemUrl;
    private String especificacao;
    private String categoria; // nome do enum CategoriaProduto, ex: "PLACA_DE_VIDEO"

    public String getNome()          { return nome; }
    public double getPreco()         { return preco; }
    public int getEstoque()          { return estoque; }
    public String getImagemUrl()     { return imagemUrl; }
    public String getEspecificacao() { return especificacao; }
    public String getCategoria()     { return categoria; }

    public void setNome(String nome)                   { this.nome = nome; }
    public void setPreco(double preco)                 { this.preco = preco; }
    public void setEstoque(int estoque)                { this.estoque = estoque; }
    public void setImagemUrl(String imagemUrl)         { this.imagemUrl = imagemUrl; }
    public void setEspecificacao(String especificacao) { this.especificacao = especificacao; }
    public void setCategoria(String categoria)         { this.categoria = categoria; }
}
