import java.io.FileWriter; 
import java.io.IOException;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.*;

public class SortAndWrite {
	private int[] casosNaoOrdenados,obitosNaoOrdenados;
	private String[] cidadesNaoOrdenados,outrasInfos;
	private String primeiraLinha;
	private ContadorExecucao contagem;

	public SortAndWrite(){}

	public SortAndWrite(int[] casos,int[] obitos,String[] cidades){
		this.contagem = new ContadorExecucao();
		this.casosNaoOrdenados = casos;
		this.obitosNaoOrdenados = obitos;
		this.cidadesNaoOrdenados = cidades;
	}

	public SortAndWrite(int[] casos,int[] obitos,String[] cidades,String[] outrasInfos,String primeiraLinha){
		this(casos,obitos,cidades);
		this.outrasInfos = outrasInfos;
		this.primeiraLinha = primeiraLinha;
	}

	public void exibirTabelaTempo(String titulo){
		contagem.printExecutionTimeTable(titulo);
	}

	// retorna a quantidade de linhas de um arquivo
	public static long getFileLines(String filename){
		long lines;
		try {
			if(!((new File(filename)).exists())){ // arquivo nao encontrado
				lines = -2;
			} else { // arquivo existe
				lines = Files.lines(Paths.get(filename),StandardCharsets.UTF_8).count();
			}
		} catch(IOException | UncheckedIOException e){ // erro na leitura das linhas
			lines = -1;
		}
		return lines;
	}
	
	public int[] getIndexesOrd2(int[] data,int[] dataNotSorted){
		int index[] = new int[dataNotSorted.length];
		
		for(int i=0;i<dataNotSorted.length;i++){
			index[i] = Arrays.binarySearch(data,dataNotSorted[i]);
		}
		
		return index;
	}

	public int[] allOcorrences(int arr[],int element){
    	return IntStream.range(0,arr.length).filter(i->element==arr[i]).toArray();
	}
	public boolean arrayContains(int arr[],int element){
		return IntStream.of(arr).anyMatch(i->i==element);
	}

	private int[] getIndexesOriginais(int[] dataOrig,int[] dataSorted){
		int originalIndexes[] = new int[dataOrig.length];
		int allIndexes[] = new int[dataOrig.length];

		for(int i=0;i<dataOrig.length;i++){
			int allFound[] = allOcorrences(dataSorted,dataOrig[i]);
			int indexFound = allFound[0];
			
			for(int index: allFound){
				if(!arrayContains(allIndexes,index)){
					indexFound = index;
				}
			}
			allIndexes[i] = indexFound;
			originalIndexes[indexFound] = i;
		}
		return originalIndexes;
	}

	// para string
	public int[] getIndexesOriginais(String[] dataOrig,String[] dataSorted){
		int originalIndexes[] = new int[dataOrig.length];
		int allIndexes[] = new int[dataOrig.length];

		for(int i=0;i<dataOrig.length;i++){
			int indexFound = Arrays.asList(dataSorted).indexOf(dataOrig[i]);
			allIndexes[i] = indexFound;
			originalIndexes[indexFound] = i;
		}

		return originalIndexes;
	}
	// para casos e obitos e cidades
	public String ordenaInformacoesGerais(int[] informacoesIndexes){
		String informacoesGerais[] = this.outrasInfos.clone();
		String dataInfoAux[] = new String[informacoesGerais.length];
		
		for(int i=0;i<dataInfoAux.length;i++){
			dataInfoAux[i] = informacoesGerais[informacoesIndexes[i]];
		}

		String dataInfo = "";

		for(int i=0;i<informacoesGerais.length;i++){
			String line = dataInfoAux[i];
			dataInfo += String.join(",",line)+"\n";
		}
		return dataInfo;
	}
	
    // escreve os 3 vetores de dados (ordenados) nos arquivos.csv
	private boolean escreverCSV(String algoritmo,int[] casos,int[] obitos,String[] cidades) {
		FileWriter writeCasos,writeObitos,writeCidades;
		final String diretorio = "../DadosOrdenados/MedioCaso";
		
		writeCasos = writeObitos = writeCidades = null;
		try {
			if(Files.notExists(Paths.get(diretorio))){
				Files.createDirectories(Paths.get(diretorio));
			}

			writeCasos = new FileWriter(String.format("%s/%s_casos.csv",diretorio,algoritmo));
			writeObitos = new FileWriter(String.format("%s/%s_obitos.csv",diretorio,algoritmo));
			if(!algoritmo.equals("countingSort")){
				writeCidades = new FileWriter(String.format("%s/%s_cidades.csv",diretorio,algoritmo));
			}

			// Escreve as informações dos Casos
			int indexesOriginais[] = getIndexesOriginais(casosNaoOrdenados,casos);
			String infosOrdenadas = ordenaInformacoesGerais(indexesOriginais);
			writeCasos.write(primeiraLinha+"\n");
			writeCasos.write(infosOrdenadas);

			// Escreve as informações dos Óbitos
			indexesOriginais = getIndexesOriginais(obitosNaoOrdenados,obitos);
			infosOrdenadas = ordenaInformacoesGerais(indexesOriginais);
			writeObitos.write(primeiraLinha+"\n");
			writeObitos.write(infosOrdenadas);
			
			// Escreve as informações das Cidades
			if(writeCidades!=null){ // desconsiderando o counting Sort
				indexesOriginais = getIndexesOriginais(cidadesNaoOrdenados,cidades);
				infosOrdenadas = ordenaInformacoesGerais(indexesOriginais);
				writeCidades.write(primeiraLinha+"\n");
				writeCidades.write(infosOrdenadas);
			}
			
			writeCasos.close();
			writeObitos.close();
			if(writeCidades!=null)
				writeCidades.close();

		} catch(IOException e){
			System.out.println("[!] SortAndWriteError: erro na escrita do metodo "+algoritmo);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String ordenarMelhorCasoCSV(){
		String cidades[] = cidadesNaoOrdenados.clone();

		AlgoritmosOrdenacao.quickSortMediana3Code(cidades);
		
		int[] indexesOriginais = getIndexesOriginais(cidadesNaoOrdenados,cidades);
		String infosOrdenadas = ordenaInformacoesGerais(indexesOriginais);

		return infosOrdenadas;
	}
	
	// * Aplicação e Escrita dos Algoritmos de Ordenação *

	// aplicação e escrita dos dados com o algoritmo BubbleSort.
	public long bubbleSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
		contagem.start("BubbleSort");

		AlgoritmosOrdenacao.bubbleSortCode(casos);
		AlgoritmosOrdenacao.bubbleSortCode(obitos);
		AlgoritmosOrdenacao.bubbleSortCode(cidades);

		execTime = contagem.stop();
        
		if(escrever){
			if(!escreverCSV("bubbleSort",casos,obitos,cidades)){
				System.err.println("[!] BubbleSortError: erro na escrita dos dados");
				execTime = -1;
			}
		}
		
		if(execTime>=0)
			System.out.println("[*] BubbleSort Efetuado com sucesso");
		return execTime;
	}

	// aplicação e escrita dos dados com o algoritmo MergeSort.
	public long mergeSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
        contagem.start("MergeSort");

		AlgoritmosOrdenacao.mergeSortCode(casos);
		AlgoritmosOrdenacao.mergeSortCode(obitos);
		AlgoritmosOrdenacao.mergeSortCode(cidades);

		execTime = contagem.stop();

		if(escrever){
			if(!escreverCSV("mergeSort",casos,obitos,cidades)){
				System.err.println("[!] MergeSortError: erro na escrita dos dados");
				execTime = -1;
			}
		}

		if(execTime>=0)
			System.out.println("[*] MergeSort Efetuado com sucesso");
		return execTime;
	}

	// aplicação e escrita dos dados com o algoritmo Counting Sort.
	public long countingSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
		contagem.start("CountingSort");

		AlgoritmosOrdenacao.countingSortCode(casos);
		AlgoritmosOrdenacao.countingSortCode(obitos);

		execTime = contagem.stop();

		if(escrever){
			if(!escreverCSV("countingSort",casos,obitos,cidades)){
				System.err.println("[!] CountingSortError: erro na escrita dos dados");
				execTime = -1;
			}
		}

		if(execTime>=0)
			System.out.println("[*] CountingSort Efetuado com sucesso");
		return execTime;
	}

	// aplicação e escrita dos dados com o algoritmo Insertion Sort.
	public long insertionSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
		contagem.start("InsertionSort");

		AlgoritmosOrdenacao.insertionSortCode(casos);
		AlgoritmosOrdenacao.insertionSortCode(obitos);
		AlgoritmosOrdenacao.insertionSortCode(cidades);

		execTime = contagem.stop();
        
		if(escrever){
			if(!escreverCSV("insertionSort",casos,obitos,cidades)){
				System.err.println("[!] InsertionSortError: erro na escrita dos dados");
				execTime = -1;
			}
		} 

		if(execTime>=0)
			System.out.println("[*] InsertionSort Efetuado com sucesso");
		return execTime;
	}

	// aplicação e escrita dos dados com o algoritmo Quick Sort.
	public long quickSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
        contagem.start("QuickSort");

		AlgoritmosOrdenacao.quickSortCode(casos);
		AlgoritmosOrdenacao.quickSortCode(obitos);
		AlgoritmosOrdenacao.quickSortCode(cidades);

		execTime = contagem.stop();
        
		if(escrever){
			if(!escreverCSV("quickSort",casos,obitos,cidades)){
				System.err.println("[!] QuickSortError: erro na escrita dos dados");
				execTime = -1;
			}
		} 

		if(execTime>=0)
			System.out.println("[*] QuickSort Efetuado com sucesso");
		return execTime;
	}

	// aplicação e escrita dos dados com o algoritmo Quick Sort.
	public long quickSortMediana3(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
        contagem.start("QuickSortMediana3");

		AlgoritmosOrdenacao.quickSortMediana3Code(casos);
		AlgoritmosOrdenacao.quickSortMediana3Code(obitos);
		AlgoritmosOrdenacao.quickSortMediana3Code(cidades);

		execTime = contagem.stop();

		if(escrever){
			if(!escreverCSV("quickSortMed3",casos,obitos,cidades)){
				System.err.println("[!] QuickSortMediana3Error: erro na escrita dos dados");
				execTime = -1;
			}
		} 

		if(execTime>=0)
			System.out.println("[*] QuickSortMediana3 Efetuado com sucesso");
		return execTime;
	}
	
	public long heapSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
        contagem.start("HeapSort");

		AlgoritmosOrdenacao.heapSortCode(casos);
		AlgoritmosOrdenacao.heapSortCode(obitos);
		AlgoritmosOrdenacao.heapSortCode(cidades);

		execTime = contagem.stop();
        
		if(escrever){
			if(!escreverCSV("heapSort",casos,obitos,cidades)){
				System.err.println("[!] HeapSortError: erro na escrita dos dados");
				execTime = -1;
			}
		} 

		if(execTime>=0)
			System.out.println("[*] HeapSort Efetuado com sucesso");
		return execTime;
	}

	public long selectionSort(boolean escrever){
		long execTime = -1;
		int[] casos = this.casosNaoOrdenados.clone();
		int[] obitos = this.obitosNaoOrdenados.clone();
		String[] cidades = this.cidadesNaoOrdenados.clone();
		
        contagem.start("SelectionSort");

		AlgoritmosOrdenacao.selectionSortCode(casos);
		AlgoritmosOrdenacao.selectionSortCode(obitos);
		AlgoritmosOrdenacao.selectionSortCode(cidades);

		execTime = contagem.stop();
        
		if(escrever){
			if(!escreverCSV("selectionSort",casos,obitos,cidades)){
				System.err.println("[!] SelectionSortError: erro na escrita dos dados");
				return -1;
			} 
		}

		if(execTime>=0)
			System.out.println("[*] SelectionSort Efetuado com sucesso");
		return execTime;
	}

}