public class Main {
	// garante que todos as classes serão compiladas antes de serem executadas
	public static void compileAll(){
		new SortAndWrite();
		new CovidDataControler();
		new AlgoritmosOrdenacao();
		new ContadorExecucao();
	}

	public static void main(String args[]){
		compileAll();

		final String arquivo = "../caso.csv"; // arquivo que sera usado para ordenacoes
		CovidDataControler covidData = new CovidDataControler();

		// Medio Caso
		System.out.println("[*] Aplicando Ordenacoes para o Medio Caso (Arquivo Original)");
		covidData.lerArquivo(arquivo);
		covidData.ordenarDados(true);
		covidData.imprimirTabelaDeExecucao("MEDIO CASO");
		
		// Pior Caso
		System.out.println("[*] Fazendo calculo do Pior Caso (Arquivo com dados invertidos)");
		covidData.calcularPiorCaso(arquivo);
		covidData.imprimirTabelaDeExecucao("PIOR CASO");
		
		// Melhor Caso
		System.out.println("[*] Fazendo calculo do Melhor Caso (Arquivo previamente ordenado)");
		covidData.calcularMelhorCaso(arquivo);
		covidData.imprimirTabelaDeExecucao("MELHOR CASO");
	}
}