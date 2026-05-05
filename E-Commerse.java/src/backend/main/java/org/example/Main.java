import java.util.Scanner;
import java.util.ArrayList;


class Produto{
    String nome;
    double preco;
}
public class Main{
    public static void main (String[]args) {

        ArrayList<Produto> produtos = new ArrayList<>();

        Produto mouse = new Produto();
        mouse.nome = "Mouse Gamer";
        mouse.preco = 149.99;

        Produto teclado = new Produto();
        teclado.nome = "Teclado Mecânico";
        teclado.preco = 299.99;

        Produto monitor = new Produto();
        monitor.nome = "Monitor";
        monitor.preco = 999.99;

        Produto gabinete  = new Produto();
        gabinete.nome = "Gabinete";
        gabinete.preco = 349.99;

        produtos.add(mouse);
        produtos.add(teclado);
        produtos.add(monitor);
        produtos.add(gabinete);


        for (Produto p : produtos) {
            System.out.println(p.nome + " R$ " + p.preco);
        }
    }
}