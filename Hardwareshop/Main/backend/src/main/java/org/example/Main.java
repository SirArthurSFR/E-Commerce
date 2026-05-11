import java.util.ArrayList;
import java.util.Scanner;

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

//Código
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Produto> carrinho = new ArrayList<>();

//Lista de produtos
        produtos.add(new Monitor("Monitor Gamer", 999.99, 144));
        produtos.add(new Monitor("Monitor Gamer", 1599.99, 240));
        produtos.add(new Mouse("Mouse", 249.99, 16000));
        produtos.add(new GPU("RTX 5060 Ti", 2999.99, 8));
        produtos.add(new GPU("RX 9070Xt", 4499.99, 16));
        produtos.add(new CPU("AMD Ryzen 9 9950X3D", 3999.99));
        produtos.add(new CPU("Intel i9-14900k", 2899.99));

        int opcao = 1;

        while (opcao !=4){
            System.out.println("Menu");
            System.out.println("1 - Abrir catálogo");
            System.out.println("2 - Adicionarproduto ao carrinho");
            System.out.println("3 - Abrir carrinho");
            System.out.println("4 - Sair");
            opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    System.out.println("Catálogo: ");
                    for (int i = 1; i <produtos.size();i++){
                        System.out.println(i + " " + produtos.get(i));
                    }
                    break;

                case 2:
                    System.out.println("Digite o número do produto: ");
                    int escolha = scanner.nextInt();

                    if (escolha >= 1 && escolha < produtos.size()) {
                    carrinho.add(produtos.get(escolha));
                    System.out.println("Produto foi adicionado ao carrinho!");
                    } else {
                        System.out.println("Opção invalida!");
                    }
                    break;

                case 3:
                    System.out.println("Carrinho:");
                    if (carrinho.isEmpty()){
                        System.out.println("Carrinho Vazio");
                    }else{
                    for (Produto p : carrinho) {
                        System.out.println(p);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }
}