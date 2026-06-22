package org.example.hardwareshop;

public class StatusInvalidoException extends RegraNegocioException {
    public StatusInvalidoException(String mensagem) {
        super(mensagem);
    }
}
