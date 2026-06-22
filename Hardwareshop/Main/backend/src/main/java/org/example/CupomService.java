package org.example.hardwareshop;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// descontos e cupons
@Service
public class CupomService {

    private final Map<String, Cupom> cupons = new HashMap<>();

    public CupomService() {
        cupons.put("GLAUBER10", new Cupom("GLAUBER10", 10, true));
        cupons.put("BEMVINDO15", new Cupom("BEMVINDO15", 15, true));
        cupons.put("HARDFORCE20", new Cupom("HARDFORCE20", 20, true));
    }

    public Cupom validar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new CupomInvalidoException("Informe o código do cupom.");
        }
        Cupom cupom = cupons.get(codigo.trim().toUpperCase());
        if (cupom == null || !cupom.isValido()) {
            throw new CupomInvalidoException("Cupom inválido ou expirado: " + codigo);
        }
        return cupom;
    }
}
