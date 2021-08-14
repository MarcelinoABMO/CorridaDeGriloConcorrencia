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

    private Semaphore semaphore;//sincroniza as threads evitando concorrencia.
    
    //registra a ordem de chegada no ranking
    private int posicaoChegada;
    public int getPosicaoChegada() { return this.posicaoChegada; }
    
    //id da equipe do grilo
    private int idEquipe;
    public int getEquipe() { return this.idEquipe; }
    
    //controla a ordem de chegada dos grilos. precisa ser sincronizado.
    public volatile static int contadorDeChegada = 0;
    
    /**
     * Construtor da classe.
     * @param index posição da instancia no array e identificação do objeto.
     * @param distanciaMax posição da linha de chegada.
     * @param idEquipe identificação da equipe do grilo.
     * @param semaphore passa a referência de um semaforo para todas as instancias de grilo.
     */
    public Grilo(int index, int distanciaMax, int idEquipe, Semaphore semaphore){
        this.indice = index;
	this.distanciaMax = distanciaMax;
        this.random = new Random();
        this.idEquipe = idEquipe;
        this.semaphore = semaphore;
    }
    
    /**
     * método responsável por executar a lógica em paralelo na thread.
     */
    @Override public void run()
    {
        //loop que mantem a thread executando até que o grilo alcance a linha de chegada
        while(posicao < distanciaMax)
        {
            try
            {
                //interrompe a execução da thread e libera o processador pelo
                //período de tempo determinado pelo parâmetro do método
                Thread.sleep(speed);
            } catch (InterruptedException ex){
                Logger.getLogger(Grilo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Pular();
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
        
        //registra a chegada
        if (posicao >= distanciaMax)
            Chegar();
        else
        //imprime um log com as informações da iteração atual
        System.out.println("O Grilo_" + indice + " do time " + (idEquipe + 1) + 
                " pulou " + pegaPulo + "cm e ja percorreu " + posicao + "cm");
    }
    
    /**
     * Registra a chegada do grilo ao cruzar a linha e sua posição no ranking.
     */
    void Chegar(){
        //Registra a posição de chegada do grilo,
        //evitando concorrência de acesso com outras threads
        //Utilizamos o exemplo do jantar dos filósofos nesta construção, porém não
        //liberamos a thread com um sleep pois o intuito é garantir a ordem de chegada
        //registrando-a o mais rápido possível, ou seja, assim que o processador estiver disponível.
        //Pode não ser a melhor alternativa pois em caso de deadlock, este while true
        //se encarregará de piorar as coisas gerando um loop infinito.
        while(true) {
            if (semaphore.tryAcquire())
            {
                contadorDeChegada++;
                this.posicaoChegada = contadorDeChegada;
                
                System.out.println("-> O Grilo_" + indice + " do time " + (idEquipe + 1) +
                        " foi o " + posicaoChegada +"º colocado com " + pulos + " pulos.");
                
                semaphore.release();
                break;
            }
        }
    }
}
