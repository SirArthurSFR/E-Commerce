package org.example.hardwareshop;

public class PagamentoInvalidoException extends RegraNegocioException {
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
