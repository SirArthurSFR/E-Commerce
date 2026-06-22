package org.example.hardwareshop;

public class CredenciaisInvalidasException extends RegraNegocioException {
    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
