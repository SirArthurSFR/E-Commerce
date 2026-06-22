package org.example.hardwareshop;

public enum CategoriaProduto {
    PLACA_DE_VIDEO("Placa de Vídeo"),
    PLACA_MAE("Placa-Mãe"),
    PROCESSADOR("Processador"),
    MEMORIA_RAM("Memória RAM"),
    SSD("SSD"),
    HD("HD"),
    FONTE("Fonte"),
    GABINETE("Gabinete"),
    COOLER("Cooler"),
    WATER_COOLER("Water Cooler"),
    VENTOINHA("Ventoinha"),
    MONITOR("Monitor"),
    NOTEBOOK("Notebook"),
    PERIFERICOS("Periféricos"),
    ACESSORIOS("Acessórios");

    private final String rotulo;

    CategoriaProduto(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
