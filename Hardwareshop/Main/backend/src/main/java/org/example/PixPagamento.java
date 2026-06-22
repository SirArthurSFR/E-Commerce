package org.example.hardwareshop;

public class PixPagamento implements Pagamento {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final String codigo;

    public PixPagamento() {
        this.codigo = gerarCodigo();
    }

    private String gerarCodigo() {
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            int index = (int) (Math.random() * CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }
        return codigo.toString();
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean validar() {
        // pix é considerado aprovado instantaneamente, dps eu faço algo mais bonitinho
        return true;
    }

    @Override
    public String processar(double valor) {
        return String.format("Pix gerado: %s | Pagamento de R$ %.2f confirmado.", codigo, valor);
    }
}
