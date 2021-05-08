import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.*;

public class CovidDataControler {
	private String[] cidades,outrasInfos;
    private int[] casos,obitos;
    private String primeiraLinha;
    private int indexCasos,indexObitos,indexCidades;
    private int erroCode;
    private long filesize;
    private SortAndWrite ordenacaoDados;

    public CovidDataControler(){
        this.cidades = null;
        this.casos = null;
        this.obitos = null;
        this.ordenacaoDados = null;
        this.primeiraLinha = null;
        this.erroCode = 0;
        this.filesize = -1;
    }

    private static void inicializaStringArr(String cidades[]){
		for(int i=0;i<cidades.length;i++){
			cidades[i] = "";
		}
	}
    // elimina as linhas vazias/nao desejadas dos vetores de dados
    private void resizeData(int newSize){
        int[] auxInt = new int[newSize];;
        String[] auxStr = new String[newSize];

        System.arraycopy(casos,0,auxInt,0,newSize);
        this.casos = auxInt.clone();
        System.arraycopy(obitos,0,auxInt,0,newSize);
        this.obitos = auxInt.clone();
        System.arraycopy(cidades,0,auxStr,0,newSize);
        this.cidades = auxStr.clone();
        System.arraycopy(outrasInfos,0,auxStr,0,newSize);
        this.outrasInfos = auxStr.clone();
    }

    // função de controle e exibição dos erros (quando ouver uma mensagem do erro especifico sera mandado e encerrara o programa)  
    private void setErro(int erroCode){
        this.erroCode = erroCode;
        String errorMsg = "";

        switch(erroCode){
			case 0:
 	           errorMsg = "arquivo vazio"; break;
        	case -1:
           		errorMsg = "erro na leitura das linhas de arquivos"; break;
        	case -2:
	            errorMsg = "arquivo nao encontrado"; break;
        	case -3:
	            errorMsg = "erro no calculo do pior caso"; break;
        	case -4:
	            errorMsg = "erro no calculo do melhor caso"; break;
        	case -5:
	            errorMsg = "erro ao imprimir a tabela de tempos"; break;
        	case -6:
	            errorMsg = "melhor caso nao pode ser calculado antes do medio caso."; break;
        	default: 
            	errorMsg = "erro na manipulacao de dados";
	    }
        
        System.err.printf("[!] Error: %s\n",errorMsg); // exibe a mensagem de erro
        System.exit(erroCode); // finaliza o programa
    }

    public void lerArquivo(String filename){
        this.filesize = SortAndWrite.getFileLines(filename);
        if(filesize<=0)
            setErro((int)filesize);

        outrasInfos = new String[(int)filesize];
        cidades = new String[(int)filesize];
        casos = new int[(int)filesize];
        obitos = new int[(int)filesize];

        inicializaStringArr(outrasInfos);
        inicializaStringArr(cidades);        
        
        int contInfo = 0;
        int colunas = 0;

        System.out.printf("\n[*] Iniciando leitura dos dados de %s (%d linhas)\n\n",filename,(int)filesize);
        try {
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
            primeiraLinha = csvReader.readLine();

            for(String info: primeiraLinha.split(",")){
                if(info.equals("city"))
                    this.indexCidades = colunas;
                else if(info.equals("confirmed") || info.equals("last_available_confirmed"))
                    this.indexCasos = colunas;
                else if(info.equals("deaths") || info.equals("last_available_deaths"))
                    this.indexObitos = colunas;
                colunas += 1;
            }

            String line = "";
            while((line = csvReader.readLine()) != null) {
		   		String[] dadosLinha = line.split(","); // informacao de cada linha
                
                if(AlgoritmosOrdenacao.validStr(dadosLinha[indexCidades]) && AlgoritmosOrdenacao.validStr(dadosLinha[indexCasos])){
                    cidades[contInfo] = dadosLinha[indexCidades];
                    casos[contInfo] = Integer.parseInt(dadosLinha[indexCasos]);
                    obitos[contInfo] = Integer.parseInt(dadosLinha[indexObitos]);
                    
                    outrasInfos[contInfo] = String.join(",",dadosLinha);
                    contInfo++;
                }
            }
            
        } catch(FileNotFoundException | NullPointerException  e){
			setErro(-2);
		} catch(IOException e){
			setErro(-1);
		}        
        resizeData(contInfo);
    }

    public void ordenarDados(boolean escrever){
        ordenacaoDados = new SortAndWrite(casos,obitos,cidades,outrasInfos,primeiraLinha);
        
        //ordenacaoDados.bubbleSort(escrever); // Algoritmo funcionando porem não utilizado
		ordenacaoDados.selectionSort(escrever);
		ordenacaoDados.insertionSort(escrever);
		ordenacaoDados.mergeSort(escrever);
		ordenacaoDados.quickSort(escrever);
	    ordenacaoDados.quickSortMediana3(escrever);
        ordenacaoDados.countingSort(escrever);
		ordenacaoDados.heapSort(escrever);

		System.out.println("\n[*] Ordenacoes efetuadas");
    }

    public void calcularPiorCaso(String filename){
        String diretorio = "../DadosOrdenados/PiorCaso";
        String filePior = String.format("%s/piorCaso.csv",diretorio);

        try{
            Files.createDirectories(Paths.get(diretorio));
            long filesizePior = SortAndWrite.getFileLines(filename);
            if(filesizePior<=0)
                setErro(-3);

            List<String> fileData = Files.lines(Paths.get(filename)).collect(Collectors.toList());
			String filelines[] = fileData.toArray(new String[0]);

			Writer writerPiorCaso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePior), "UTF-8"));
            if(writerPiorCaso==null) throw new IOException("[!] error");

            writerPiorCaso.write(filelines[0]+"\n"); // escreve a primeira linha

            for(int i=filelines.length-1;i>0;i--){
                if(AlgoritmosOrdenacao.validStr(filelines[i]))
                    writerPiorCaso.write(filelines[i]+"\n");
            }

            writerPiorCaso.close();
        } catch (IOException e) {
			setErro(-3);
		}
        this.lerArquivo(filePior);
        this.ordenarDados(false);
    }

    public void calcularMelhorCaso(String filename){
        if(cidades==null){
            setErro(-6);
        }

        String diretorio = "../DadosOrdenados/MelhorCaso";
        String fileMelhor = String.format("%s/melhorCaso.csv",diretorio);
		
        try{
            // cria o diretorio para o melhor caso
            Files.createDirectories(Paths.get(diretorio));
            
            Writer writerMelhorCaso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileMelhor),"UTF-8"));
			if(writerMelhorCaso==null) throw new IOException("[!] error");

            SortAndWrite ordenacaoMelhorCaso = new SortAndWrite(casos,obitos,cidades,outrasInfos,primeiraLinha);
            String infosOrdenadas = ordenacaoMelhorCaso.ordenarMelhorCasoCSV();
            
			writerMelhorCaso.write(primeiraLinha+"\n");
            writerMelhorCaso.write(infosOrdenadas);
            
            writerMelhorCaso.close();
            
        } catch (IOException | UncheckedIOException e){
			setErro(-4);
		}
        this.lerArquivo(fileMelhor);
        this.ordenarDados(false);
    }

    public void imprimirTabelaDeExecucao(String titulo){
        try {
            PrintStream tempStdout = System.out;
            System.setOut(new PrintStream(new FileOutputStream("../DadosOrdenados/TimesExecution.txt",true)));
            
            if(titulo.equals("MEDIO CASO")){ // caso padrao
                new PrintStream(new File("../DadosOrdenados/TimesExecution.txt"));// limpa o antigo arquivo de tempos 
    			System.out.println("\n\t\tTEMPOS DE EXECUÇÕES DOS ALGORITMOS DE ORDENAÇÃO (MEDIO, PIOR E MELHOR CASO)");
            }
            
            if(ordenacaoDados==null) throw new Exception();
            ordenacaoDados.exibirTabelaTempo(titulo);
            
            System.setOut(tempStdout);
        } catch(Exception e) {
             setErro(-5);
        }
        ordenacaoDados.exibirTabelaTempo(titulo);
    }
}