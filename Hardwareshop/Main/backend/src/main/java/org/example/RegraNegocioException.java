package org.example.hardwareshop;

// Exceção genérica para violações de regra de negócio (resulta em HTTP 400)
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
