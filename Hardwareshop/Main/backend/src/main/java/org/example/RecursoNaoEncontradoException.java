package org.example.hardwareshop;

// Exceção para recursos inexistentes, ex: produto ou pedido (resulta em HTTP 404)
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
