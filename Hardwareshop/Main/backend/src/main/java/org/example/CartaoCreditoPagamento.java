package org.example.hardwareshop;

public class CartaoCreditoPagamento implements Pagamento {

    private final String numero;
    private final String cvv;
    private final String validade;

    public CartaoCreditoPagamento(String numero, String cvv, String validade) {
        this.numero = numero;
        this.cvv = cvv;
        this.validade = validade;
    }

    // isso aqui valida os dados do cartao
    @Override
    public boolean validar() {
        if (numero == null || !numero.matches("\\d{16}")) {
            return false;
        }
        if (cvv == null || !cvv.matches("\\d{3}")) {
            return false;
        }
        if (validade == null || !validade.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        return true;
    }

    @Override
    public String processar(double valor) {
        String final4 = numero.substring(numero.length() - 4);
        return String.format("Pagamento de R$ %.2f aprovado no cartão final %s", valor, final4);
    }
}
