package org.example.hardwareshop;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

// Cadastro/login "temporário": tudo guardado em memória (listas/mapas),
// exatamente como foi feito com o estoque de produtos. Os dados somem quando
// a aplicação Spring Boot é reiniciada — não existe banco de dados.
@Service
public class UsuarioService {

    private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$";

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Integer> sessoes = new HashMap<>(); // token -> usuarioId
    private final AtomicInteger proximoId = new AtomicInteger(1);

    // Regra: cadastro de usuário com validações básicas
    public Usuario cadastrar(CadastroRequest dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RegraNegocioException("Informe o nome completo.");
        }
        if (dto.getEmail() == null || !dto.getEmail().trim().matches(REGEX_EMAIL)) {
            throw new RegraNegocioException("Informe um e-mail válido.");
        }
        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new RegraNegocioException("A senha deve ter no mínimo 6 caracteres.");
        }

        String emailNormalizado = dto.getEmail().trim().toLowerCase();
        boolean jaExiste = usuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(emailNormalizado));
        if (jaExiste) {
            throw new EmailJaCadastradoException("Já existe uma conta cadastrada com este e-mail.");
        }

        String salt = SenhaUtil.gerarSalt();
        String hash = SenhaUtil.gerarHash(dto.getSenha(), salt);

        Usuario usuario = new Usuario(proximoId.getAndIncrement(), dto.getNome().trim(), emailNormalizado, hash, salt);
        usuarios.add(usuario);
        return usuario;
    }

    // Regra: login - valida credenciais e abre uma "sessão" em memória (token)
    public SessaoResponse autenticar(LoginRequest dto) {
        if (dto.getEmail() == null || dto.getSenha() == null) {
            throw new CredenciaisInvalidasException("Informe e-mail e senha.");
        }

        String emailNormalizado = dto.getEmail().trim().toLowerCase();
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(emailNormalizado))
                .findFirst()
                .orElse(null);

        // mensagem genérica de propósito: não revela se o erro foi no e-mail ou na senha
        if (usuario == null || !usuario.senhaCorreta(dto.getSenha())) {
            throw new CredenciaisInvalidasException("E-mail ou senha incorretos.");
        }

        String token = UUID.randomUUID().toString();
        sessoes.put(token, usuario.getId());
        return new SessaoResponse(token, usuario);
    }

    public Usuario buscarPorToken(String token) {
        if (token == null) {
            throw new CredenciaisInvalidasException("Sessão inválida ou expirada. Faça login novamente.");
        }
        Integer usuarioId = sessoes.get(token);
        if (usuarioId == null) {
            throw new CredenciaisInvalidasException("Sessão inválida ou expirada. Faça login novamente.");
        }
        return usuarios.stream()
                .filter(u -> u.getId() == usuarioId)
                .findFirst()
                .orElseThrow(() -> new CredenciaisInvalidasException("Usuário da sessão não encontrado."));
    }

    public void encerrarSessao(String token) {
        if (token != null) {
            sessoes.remove(token);
        }
    }
}
