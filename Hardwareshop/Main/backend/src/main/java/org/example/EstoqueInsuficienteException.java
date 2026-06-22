package org.example.hardwareshop;

public class EstoqueInsuficienteException extends RegraNegocioException {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
