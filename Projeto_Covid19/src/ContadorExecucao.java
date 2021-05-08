public class ContadorExecucao {
    private final int MAX_ALGORITMOS = 8;
	private long startTime,executionTimes[];
    private String[] algoritmos;
    private int contagemExecucoes;

    public ContadorExecucao(){
        this.executionTimes = new long[MAX_ALGORITMOS];
		this.algoritmos = new String[MAX_ALGORITMOS];
        this.contagemExecucoes = 0;
    }

    // inicia o contador
    public void start(String algoritmo){
        startTime = System.nanoTime();
        algoritmos[contagemExecucoes] = algoritmo;
    }

    // para o contador e retorna o tempo de contagem
    public long stop(){
        if(startTime==0) return -2;
        
        long stopTime = System.nanoTime();
        long execTime = stopTime-startTime;

        executionTimes[contagemExecucoes] = execTime;
        contagemExecucoes++;

        this.startTime = 0;
        return execTime;
    }

    // repete uma string uma determinada quantidade de vezes
	public static String repeatStr(String str,int n){
		return new String(new char[n]).replace("\0",str);
	}
	// formata uma string colocando os devidos espaçamentos necessários para exibição na tabela
	private String formatStr(String str,int column){
		int size;
		String formated = "    "+str;

		if(column==0) size=22; // tamanho da string de titulo da primeira coluna
		else if(column==1) size=36; // tamanho da string de titulo da segunda coluna
		else if(column==2) size=0; // terceira coluna não precisa de espaços
		else return "";

		if(column<2 && size-str.length() > 0) // calcula a quantidade de espaços que sera necessarios apos a string
			formated += new String(new char[size-str.length()]).replace("\0"," ");
		
		return formated;
	}

    // imprime uma tabela com o tempo de execução e os respectivos algoritmos correspondentes a cada um dos tempos
	public void printExecutionTimeTable(String titulo){
		System.out.printf("\n\t\t\t\t [*] %s [*]\n",titulo);
		System.out.println("\t\t\t\t"+repeatStr("_",20));

		System.out.println(repeatStr("-",100));
		System.out.println("Algoritmo de Ordenacao    |    Tempo de Execucao (Milisegundos)    |    Tempo de Execucao (Segundos)");
		System.out.println(repeatStr("-",100));

		for(int i=0;i<executionTimes.length;i++){
			String algoritmo = algoritmos[i];
			if(algoritmo==null) break;

			double milisec = (double)executionTimes[i]/1000000;
			double sec = (double)executionTimes[i]/1000000000;

			String algoritmoFormat = formatStr(algoritmo,0);
			String milisecFormat = formatStr(String.format("%f",milisec), 1);
			String secFormat = formatStr(String.format("%f",sec), 2);
			
			System.out.printf("%s|%s|%s\n",algoritmoFormat,milisecFormat,secFormat);
		}
		System.out.println();
	}
}