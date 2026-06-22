package org.example.hardwareshop;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private String salt;

    public Usuario(int id, String nome, String email, String senhaHash, String salt) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.salt = salt;
    }

    public int getId()       { return id; }
    public String getNome()  { return nome; }
    public String getEmail() { return email; }

    // nunca expor o hash/salt da senha no JSON de resposta
    @JsonIgnore
    public String getSenhaHash() { return senhaHash; }

    @JsonIgnore
    public String getSalt() { return salt; }

    public boolean senhaCorreta(String senhaDigitada) {
        if (senhaDigitada == null) {
            return false;
        }
        return senhaHash.equals(SenhaUtil.gerarHash(senhaDigitada, salt));
    }
}
