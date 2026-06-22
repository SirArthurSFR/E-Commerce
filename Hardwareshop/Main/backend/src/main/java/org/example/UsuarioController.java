package org.example.hardwareshop;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /usuarios/cadastro → cria uma nova conta
    @PostMapping("/cadastro")
    public Usuario cadastrar(@RequestBody CadastroRequest dto) {
        return usuarioService.cadastrar(dto);
    }

    // POST /usuarios/login → autentica e devolve um token de sessão (em memória)
    @PostMapping("/login")
    public SessaoResponse login(@RequestBody LoginRequest dto) {
        return usuarioService.autenticar(dto);
    }

    // GET /usuarios/me → retorna o usuário logado (header: Authorization: Bearer <token>)
    @GetMapping("/me")
    public Usuario me(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return usuarioService.buscarPorToken(extrairToken(authorization));
    }

    // POST /usuarios/logout → encerra a sessão atual
    @PostMapping("/logout")
    public void logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        usuarioService.encerrarSessao(extrairToken(authorization));
    }

    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring("Bearer ".length()).trim();
    }
}
