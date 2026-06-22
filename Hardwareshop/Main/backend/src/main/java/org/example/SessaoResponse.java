package org.example.hardwareshop;

// Resposta de um login bem-sucedido: token de sessão + dados do usuário
public class SessaoResponse {

    private String token;
    private Usuario usuario;

    public SessaoResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken()    { return token; }
    public Usuario getUsuario() { return usuario; }
}
