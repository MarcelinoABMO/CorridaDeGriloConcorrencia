package corridadegriloconcorrencia;

import java.util.Scanner;

/**
 *
 * @author Gustavo Eliseu, Lucas, Marcelino
 */
public class Main {
    
    public static class Equipe{
        int id;
        int distanciaPercorrida;
        int totalPulos;
    }
    
    private static Equipe[] equipes;
    
    public static void main(String[] args) {
        //pegando os inputs
        //pegando a quantidade de grilos
        System.out.print("Quantos grilos devem correr? ");
        Scanner scan = new Scanner(System.in);
        int quantGrilos = scan.nextInt();
            //pegando a posição da linha de chegada
        System.out.print("Qual a distancia para a chegada? ");
        int distanciaTotal = scan.nextInt();
        
        final int grilosPorEquipe = 3;
        int quantEquipes = quantGrilos/ grilosPorEquipe;
        equipes = new Equipe[quantEquipes];
             
        for(int i = 0; i < quantEquipes; i++){
            equipes[i] = new Equipe();
            equipes[i].id = i;
        }
        
        //instanciando os grilos e armazenando em um array
        Grilo[] grilos = new Grilo[quantGrilos];
        for (int i = 0; i < quantGrilos; i++)
        {
            grilos[i] = new Grilo((i+1), distanciaTotal);
        }
        
        //executando as threads
        for (Grilo g : grilos)
            g.start();
        
        //aguardando as threads terminarem
        for (Grilo g : grilos)
        {
            try {
                g.join();
            } catch (InterruptedException ex) {
                System.out.println("Deu merda no join das threads.");
            }
        }
        
        //imprimindo o resultado final
        System.out.println("\nResultado Final:");
        for (int i = 0; i < quantGrilos; i++)
        {
            System.out.println("O Grilo_" + (i+1) + " percorreu um total de " +
                    grilos[i].getPosicao() + "cm com " + grilos[i].getPulos() );
        }
    }
}
