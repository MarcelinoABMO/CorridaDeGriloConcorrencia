package corridadegriloconcorrencia;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        //gera equipes para agrupar os grilos
        final int grilosPorEquipe = 3;
        int quantEquipes = (quantGrilos / grilosPorEquipe) + (quantGrilos % grilosPorEquipe);
        equipes = new Equipe[quantEquipes];
             
        for(int i = 0; i < quantEquipes; i++){
            equipes[i] = new Equipe();
            equipes[i].id = i;
        }
        
        //semaforo único passado a todas as instancias de grilos para sincronizá-los.
        Semaphore semaphore = new Semaphore(1);
        
        //instanciando os grilos e armazenando em um array
        Grilo[] grilos = new Grilo[quantGrilos];
        for (int i = 0; i < quantGrilos; i++)
        {
            grilos[i] = new Grilo((i+1), distanciaTotal, (i % quantEquipes), semaphore);
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
                Logger.getLogger(Grilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //imprimindo o resultado final
        System.out.println("\nResultado Final:");
        for (int i = 0; i < quantGrilos; i++)
        {
            int idEquipe = grilos[i].getEquipe();
            equipes[idEquipe].distanciaPercorrida += grilos[i].getPosicao();
            equipes[idEquipe].totalPulos += grilos[i].getPulos();
        }

        for (int i = 0; i < quantEquipes; i++)
        {
            System.out.println("Time " + (i + 1) + ": Total de Pulos: " + equipes[i].totalPulos + 
                    " - Distancia Percorrida: " + equipes[i].distanciaPercorrida);
        }

        int equipeVencedora = 0;
        for (int i = 0; i < quantGrilos; i++) {
            if (grilos[i].getPosicaoChegada() == 1)
                equipeVencedora = grilos[i].getEquipe() + 1;
        }

        System.out.println("Time " + equipeVencedora + " foi o vencedor.");
    }
}
