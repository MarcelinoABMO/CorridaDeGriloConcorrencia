package corridadegriloconcorrencia;

import java.util.Random;

public class Grilo extends Thread{
    
    private Random random;      //gera um valor pseudo-aleatório
    
    private int posicao;        //armazena a posição acumulada durante o percurso
    public int getPosicao() { return this.posicao; }
    
    //armazena a posição desta instancia no array na classe main e serve
    //como identificação no log para o objeto
    private int indice;
    
    //tempo de espera da thread em milisegundos passada para o metodo sleep
    private final int speed = 500;
    
    private int distanciaMax;   //linha de chegada definida pelo usuário
    
    private int pulos;          //armazena a quantidade de pulos dados
    public int getPulos() { return this.pulos; }

    /**
     * Construtor da classe.
     * @param index posição da instancia no array e identificação do objeto.
     * @param distanciaMax posição da linha de chegada.
     */
    public Grilo(int index, int distanciaMax){
        this.indice = index;
	this.distanciaMax = distanciaMax;
        this.random = new Random();
    }
    
    /**
     * método responsável por executar a lógica em paralelo na thread.
     */
    @Override public void run()
    {
        //loop que mantem a thread executando até que o grilo alcance a linha de chegada
        while(posicao < distanciaMax)
        {
            Pular();

            try
            {
                //interrompe a execução da thread e libera o processador pelo
                //período de tempo determinado pelo parâmetro do método
                Thread.sleep(speed);
            } catch (InterruptedException ex){
                System.out.println("deu merda no sleep da thread");
            }
        }
    }
    
    /**
     * Função responsável pela lógica do pulo do grilo.
     */
    void Pular(){
        
        //gera um número inteiro entre 0 e 10 para representar o tamanho do pulo
        int pegaPulo = random.nextInt(10);
        
        //incrementa a posição armazenada com o valor gerado nessa iteração
        posicao += pegaPulo;
        
        //incrementa a quantidade de pulos em 1 a cada iteração
	pulos++;
        
        //imprime um log com as informações da iteração atual
        System.out.println("O Grilo_" + indice + " pulou " + pegaPulo +
                "cm e ja percorreu " + posicao + "cm");
    }
}