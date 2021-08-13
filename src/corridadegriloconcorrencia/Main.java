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
        int quantEquipes = quantGrilos / grilosPorEquipe;
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
        for (int i = 0; i < quantGrilos; i++)
		{
			idEquipe = grilo[i].id;
			equipe [idEquipe].distanciaPercorrida += grilo[i].getPosicao();
			equipe [idEquipe].totalPulos += grilo[i].getPulos();
		}
		
		for (int i = 0; i < quantGrilos; i++)
		{
			idEquipe = grilo[i].id;
			equipe[idEquipe].distanciaPercorrida += grilo[i].getPosicao();
			equipe[idEquipe].totalPulos += grilo[i].getPulos();
		}
		
		for (int i = 0; i < quantEquipes; i++)
		{
			System.out.println(“Time “ + (i + 1) + “: Total de Pulos: “ + equipe[i].totalPulos + “ 
			- Distancia Percorrida: “ + equipe[i].distanciaPercorrida);
		}
		
		int equipeVencedora;
		for (int i = 0; i < quantGrilos; i++) {
			if (grilo.posicaoChegada == 1)
				equipeVencedora = equipes[ grilo[i].idEquipe ]
		}
		
		System.out.println(“Time “ + equipeVencedora + “ foi o vencedor.”);
    }
}
