import java.util.ArrayList;
import java.util.Scanner;


interface Pagamento {
    void pagar(double valor);
}

class CartaodeCredito implements Pagamento {
    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento no cartão realizado: R$ " + valor);
    }
}

class Pix implements Pagamento {
    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento via Pix realizado: R$ " + valor);
    }
}

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

    public static String gerarPix(){
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String codigo = "";

        for(int i = 0; i < 30; i++){
            int index = (int)(Math.random()*caracteres.length());
            codigo += caracteres.charAt(index);
        }
        return codigo;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Produto> carrinho = new ArrayList<>();
        ArrayList<Produto> pedidos = new ArrayList<>();

//Lista de produtos
        produtos.add(new Monitor("Monitor Gamer", 999.99, 144));
        produtos.add(new Monitor("Monitor Gamer", 1599.99, 240));
        produtos.add(new Mouse("Mouse", 249.99, 16000));
        produtos.add(new GPU("RTX 5060 Ti", 2999.99, 8));
        produtos.add(new GPU("RX 9070Xt", 4499.99, 16));
        produtos.add(new CPU("AMD Ryzen 9 9950X3D", 3999.99));
        produtos.add(new CPU("Intel i9-14900k", 2899.99));

        int opcao = 1;

        while (opcao != 5) {
            System.out.println("Menu");
            System.out.println("1 - Abrir catálogo");
            System.out.println("2 - Adicionarproduto ao carrinho");
            System.out.println("3 - Abrir carrinho");
            System.out.println("4 - Acompanhar pedido");
            System.out.println("5 - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Catálogo: ");
                    for (int i = 1; i < produtos.size(); i++) {
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
                    if (carrinho.isEmpty()) {
                        System.out.println("Carrinho Vazio");

                    } else {

                        double total = 0;

                        for (Produto p : carrinho) {
                            System.out.println(p);
                            total += p.preco;
                        }

                        System.out.println("Deseja utilizar um cupom? ");
                        System.out.println("1-Sim | 2-Não");
//add cupom 162 a 174
                        int Cupom = scanner.nextInt();

                        if(Cupom == 1){
                            System.out.println("Digite o cupom: ");
                            String cupom = scanner.next();

                            if(cupom.equals("GLAUBER10")){
                                total *= 0.9;
                                System.out.println("Cupom aplicado! Novo total: R$ " + total);
                            }else{
                                System.out.println("Cupom inválido");
                            }
                        }
//efetuar compra
                        System.out.println("Valor total: R$ " + total);
                        System.out.println("Finalizar compra? ");
                        System.out.println("1-Sim | 2-Não ");

                        int finalizar = scanner.nextInt();
//escolhendo a forma de pagamento
                        if (finalizar == 1) {
                            System.out.println("Formas de Pagamento:");
                            System.out.println("1-Cartão");
                            System.out.println("2-Pix");

                            int tipoPagamento = scanner.nextInt();
//cartão
                            if (tipoPagamento == 1) {

                                System.out.println("Cartão (16 digitos):");
                                String numero = scanner.next();

                                System.out.println("CVV:");
                                String cvv = scanner.next();

                                System.out.println("Validade (MM/AA):");
                                String validade = scanner.next();

                                if (validade.matches("\\d{2}/\\d{2}")){
                                    System.out.println("Validade Ok");
                                }else{
                                    System.out.println("Validade inválida");
                                }

                                if (numero.length() == 16 && cvv.length() == 3) {
                                    Pagamento pagamento = new CartaodeCredito();
                                    pagamento.pagar(total);

                                    pedidos.addAll(carrinho);
                                    carrinho.clear();

                                    System.out.println("Pedido realizado com sucesso");

                                } else {
                                    System.out.println("Dados invalidos!");
                                }
//pix
                            } else if (tipoPagamento == 2) {

                                String codigoPix = gerarPix();

                                System.out.println("Código Pix gerado: ");
                                System.out.println(codigoPix);

                                System.out.println("Pagamento aprovado");

                                Pagamento pagamento = new Pix();
                                pagamento.pagar(total);

                                pedidos.addAll(carrinho);
                                carrinho.clear();

                                System.out.println("Pagamento confirmado via Pix");
                            }
                        }
                        break;
                    }


                case 4:
                    System.out.println("Pedidos:");

                    if(pedidos.isEmpty()){
                        System.out.println("Nenhum pedido foi efetuado!");
                    }else{
                        for(Produto p: pedidos){
                            System.out.println(p.nome + "Pedido aprovado, aguardando envio!");
                        }
                        break;
                    }


            case 5:
                System.out.println("Exit");
                break;
            default:
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}
