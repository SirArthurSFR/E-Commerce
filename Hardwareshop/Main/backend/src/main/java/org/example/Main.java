import java.util.ArrayList;


class Produto {
    String nome;
    double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " R$ " + preco;
    }
}

// Monitor
class Monitor extends Produto {
    int hz;

    public Monitor(String nome, double preco, int hz) {
        super(nome, preco);
        this.hz = hz;
    }

    @Override
    public String toString() {
        return nome + " " + hz + "Hz R$ " + preco;
    }
}

// Mouse
class Mouse extends Produto {
    int dpi;

    public Mouse(String nome, double preco, int dpi) {
        super(nome, preco);
        this.dpi = dpi;
    }

    @Override
    public String toString() {
        return nome + " " + dpi + "Dpi R$ " + preco;
    }
}

// GPU
class GPU extends Produto {
    int Vram;

    public GPU(String nome, double preco, int Vram) {
        super(nome, preco);
        this.Vram = Vram;
    }

    @Override
    public String toString() {
        return nome + " " + Vram + "Gb R$ " + preco;
    }
}

//CPU
class CPU extends Produto {

    public CPU(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public String toString() {
        return nome + " " + " R$ " + preco;
    }
}

//Lista dos produtos
public class Main {
    public static void main(String[] args) {

        ArrayList<Produto> produtos = new ArrayList<>();

        produtos.add(new Monitor("Monitor Gamer", 999.99, 144));
        produtos.add(new Monitor("Monitor Gamer", 1599.99, 240));
        produtos.add(new Mouse("Mouse", 249.99, 16000));
        produtos.add(new GPU("RTX 5060 Ti", 2999.99, 8));
        produtos.add(new GPU("RX 9070Xt", 4499.99, 16));
        produtos.add(new CPU("AMD Ryzen 9 9950X3D", 3999.99));
        produtos.add(new CPU("i9-14900k", 2899.99));

        for (Produto p : produtos) {
            System.out.println(p);
        }
    }
}