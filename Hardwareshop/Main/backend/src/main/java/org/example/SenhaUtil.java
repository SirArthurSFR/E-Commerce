package org.example.hardwareshop;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// Utilitário para gerar salt e hash de senha (SHA-256 + salt), sem depender
// de bibliotecas externas (ex: Spring Security Crypto) que exigiriam novas
// dependências no pom.xml.
public class SenhaUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String gerarHash(String senha, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash de senha", e);
        }
    }
}
