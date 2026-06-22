package org.example.hardwareshop;

public interface Pagamento {
    boolean validar();
    String processar(double valor);
}
