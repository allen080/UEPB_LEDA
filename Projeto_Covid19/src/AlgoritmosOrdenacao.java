import java.text.Collator;

public class AlgoritmosOrdenacao {
	// * Funções de Arrays * 

    // copia um array de inteiros
	private static int[] copiarArr(int vet[],int posIni,int posFim){
		int copied[] = new int[posFim-posIni+1];
		int cont = 0;

		for(int i=posIni;i<=posFim;i++){
			copied[cont++] = vet[i];
		}
		return copied;
	}
	// copia um array de String
	public static String[] copiarArr(String vet[],int posIni,int posFim){
		String copied[] = new String[posFim-posIni+1];
		int cont = 0;

		for(int i=posIni;i<=posFim;i++){
			copied[cont++] = vet[i];
		}
		return copied;
	}
	// troca dois valores em um array de inteiros
	public static void trocaValores(int arr[],int elem1,int elem2) {
        int aux = arr[elem1];
        arr[elem1] = arr[elem2];
        arr[elem2] = aux; 
    }
	// troca dois valores em um array de Strings
	public static void trocaValores(String arr[],int elem1,int elem2) {
        String aux = arr[elem1];
        arr[elem1] = arr[elem2];
        arr[elem2] = aux; 
    }

	// * Funções de Strings *

	// verifica se uma string é valida
	public static boolean validStr(String str){
		if(str==null || str=="" || str.length()<=1)
			return false;
		return true;
	}
	// verifica se duas strings são validas
	public static boolean validStrings(String str1,String str2){
		return validStr(str1) && validStr(str2);
	}

	// compara se uma string é menor que a outra
	public static boolean compareStr(String str1,String str2){		
		Collator comparation = Collator.getInstance(); // Objeto auxiliar de comparação que trata de strings acentuadas
		comparation.setStrength(Collator.CANONICAL_DECOMPOSITION);
	
		if(comparation.compare(str1.toLowerCase(),str2.toLowerCase()) < 0 )
			return true;
		return false;
	}

    // * Algoritmos de Ordenacao *
    
	// Aplica o MergeSort em inteiros
	public static void mergeSortCode(int vet[]){ // Algoritmo Merge Sort para Inteiros
		if(vet.length<2) return; // sai do programa se o vetor conter 1 ou 0 elementos
		
		int meio,primeiraMet[],segundaMet[],contPrim,contSeg,contOrd;

		meio = vet.length/2; // pega o indice da metade do vetor
		primeiraMet = copiarArr(vet,0,meio-1); // copia o vetor do começo até a metade (esquerda)
		segundaMet = copiarArr(vet,meio,vet.length-1); // copia o vetor da metade até o fim (direita)

		// divide novamente as partes do vetor em subvetores
		
		mergeSortCode(primeiraMet);
		mergeSortCode(segundaMet);
		contPrim = contSeg = contOrd = 0;

		// efetua a ordenação e junção das partes, comparando os valores das duas metades e colocando o menor deles no vetor original
		while(contPrim<primeiraMet.length && contSeg<segundaMet.length){
			if(primeiraMet[contPrim]!=-1 && segundaMet[contSeg]!=-1 && primeiraMet[contPrim]<segundaMet[contSeg]){
				vet[contOrd++] = primeiraMet[contPrim++];
			} else {
				vet[contOrd++] = segundaMet[contSeg++];
			}
		}
		// pega o resto dos valores que faltaram das duas metades e soma eles no final do vetor original
		while(contPrim<primeiraMet.length)
			vet[contOrd++] = primeiraMet[contPrim++];
		while(contSeg<segundaMet.length)
			vet[contOrd++] = segundaMet[contSeg++];
	}

	// Aplica o mergeSort em Strings
	public static void mergeSortCode(String vet[]){ // Algoritmo Merge Sort para Inteiros
		if(vet.length<2) return; // sai do programa se o vetor conter 1 ou 0 elementos
		
		int meio,contPrim,contSeg,contOrd;
		String primeiraMet[],segundaMet[];
		contPrim = contSeg = contOrd = 0;

		meio = vet.length/2; // pega o indice da metade do vetor
		primeiraMet = copiarArr(vet,0,meio-1); // copia o vetor do começo até a metade (esquerda)
		segundaMet = copiarArr(vet,meio,vet.length-1); // copia o vetor da metade até o fim (direita)

		// divide novamente as partes do vetor em subvetores		
		mergeSortCode(primeiraMet);
		mergeSortCode(segundaMet);
		contPrim = contSeg = contOrd = 0;

		// efetua a ordenação e junção das partes, comparando os valores das duas metades e colocando o menor deles no vetor original
		while(contPrim<primeiraMet.length && contSeg<segundaMet.length){				
			if(validStr(primeiraMet[contPrim]) && validStr(segundaMet[contSeg]) && compareStr(primeiraMet[contPrim],segundaMet[contSeg]))
				vet[contOrd++] = primeiraMet[contPrim++];
			else
				vet[contOrd++] = segundaMet[contSeg++];
		}
		// pega o resto dos valores que faltaram das duas metades e soma eles no final do vetor original
		while(contPrim<primeiraMet.length)
			vet[contOrd++] = primeiraMet[contPrim++];
		while(contSeg<segundaMet.length)
			vet[contOrd++] = segundaMet[contSeg++];
	}

	// bubble sort code para inteiros
	public static void bubbleSortCode(int arr[]){
		for(int c=0;c<arr.length;c++){
			for(int i=0;i<arr.length-1;i++){
				if(arr[i]>arr[i+1])
					trocaValores(arr,i,i+1);
			}
		}
	}
	// bubble sort para Strings
	public static void bubbleSortCode(String arr[]){
		for(int c=0;c<arr.length;c++){
			for(int i=0;i<arr.length-1;i++){
				if(validStrings(arr[i],arr[i+1]) && !compareStr(arr[i],arr[i+1]))
					trocaValores(arr,i,i+1);
			}
		}
	}

	// Calcula o pivô e faz o particionamento para o QuickSort de inteiros
	public static int particionaQuickSort(int vet[],int ini,int fim){
		int pivo,esquerda,direita;
		boolean finalizou = false;

		pivo = vet[ini]; // o elemento pivo da particao começa sendo o primeiro elemento do array 
		esquerda = ini+1; // o elemento seguinte ao pivo, que sera percorrido da esquerda pra direita
		direita = fim;// o ultimo elemento do array que percorrerá da direita pra esquerda

		while(!finalizou){
			while(esquerda<=direita && vet[esquerda]<=pivo) // movimenta a parte da esquerda do array 1 casa pra direita
           		esquerda++;
			while(vet[direita]>=pivo && direita>=esquerda) // movimenta a parte direita do array pra esquerda
	           direita--;
	       	
	       	if(esquerda<direita){// troca o elemento da esquerda com o da direita
				trocaValores(vet,esquerda,direita);
	       	} else {
	       		finalizou = true; // finaliza o loop
	       	}
		}
		// muda o valor inicial pra o elemento da direita encontrado
		trocaValores(vet,ini,direita);
  		// o elemento da direita se torna o indice particionado que precisamos
		return direita;
	}
	// Calcula o pivô e faz o particionamento para o QuickSort de Strings
	public static int particionaQuickSort(String vet[],int ini,int fim){
		String pivo;
		int esquerda,direita;
		boolean finalizou = false;

		pivo = vet[ini]; // o elemento pivo da particao começa sendo o primeiro elemento do array 
		esquerda = ini+1; // o elemento seguinte ao pivo, que sera percorrido da esquerda pra direita
		direita = fim;// o ultimo elemento do array que percorrerá da direita pra esquerda

		while(!finalizou){
			while( esquerda<=direita && compareStr(vet[esquerda],pivo) ) // movimenta a parte da esquerda do array 1 casa pra direita
           		esquerda++;
			while(!compareStr(vet[direita],pivo) && direita>=esquerda) // movimenta a parte direita do array pra esquerda
	           direita--;
	       	
	       	if(esquerda<direita){// troca o elemento da esquerda com o da direita
				trocaValores(vet,esquerda,direita);
	       	} else {
	       		finalizou = true; // finaliza o loop
	       	}
		}
		// muda o valor inicial pra o elemento da direita encontrado
		trocaValores(vet,ini,direita);
  		 // o elemento da direita se torna o indice particionado que precisamos
		return direita;
	}

	// função base do QuickSort de inteiros
	public static void quickAndSort(int vet[],int ini,int fim){
		int particionado;

		if(ini<fim){
			particionado = particionaQuickSort(vet,ini,fim); // procura dentro do array um indice particionado que sera usado para ordenar
			quickAndSort(vet,ini,particionado-1); // percorre o vetor do inicio ate a particao encontrada
			quickAndSort(vet,particionado+1,fim); // percorre o vetor do elemento seguinte da particao ate o fim
		}
	}
	// função base do QuickSort de Strings
	public static void quickAndSort(String vet[],int ini,int fim){
		int particionado;

		if(ini<fim){
			particionado = particionaQuickSort(vet,ini,fim); // procura dentro do array um indice particionado que sera usado para ordenar
			quickAndSort(vet,ini,particionado-1); // percorre o vetor do inicio ate a particao encontrada
			quickAndSort(vet,particionado+1,fim); // percorre o vetor do elemento seguinte da particao ate o fim
		}
	}

	// aplica o algoritmo QuickSort para inteiros
	public static void quickSortCode(int vet[]){
		quickAndSort(vet,0,vet.length-1);
	}
	// aplica o algoritmo QuickSort para Strings
	public static void quickSortCode(String vet[]){
		quickAndSort(vet,0,vet.length-1);
	}

	// calcula o valor do pivô com base no algoritmo de mediana de 3
	public static int getPivotMedDe3Index(int arr[],int ini,int fim){
		int pivotIndex,mediana,max,med,min;
		int arrSize = fim;
		pivotIndex = 0;
		max = med = min = ini;
		
		// acha o elemento do meio do vetor
		if(arrSize%2==0)
			med = (arrSize/2)-1;
		else
			med = arrSize/2;
		// cria um vetor auxiliar com os valores do comeco, meio e fim do vetor
		int minMedMax[] = {arr[ini], arr[med], arr[fim]};

		for(int i=0;i<3;i++){ // faz a ordenacao desse vetor auxiliar, e por consequencia o elemento do meio sera o pivô desejado
			if(minMedMax[0]>minMedMax[i])
				trocaValores(minMedMax,0,i);
			if(minMedMax[2]<minMedMax[i])
				trocaValores(minMedMax,2,i);
		}
		// busca comparando o elemento do meio do vetor auxiliar (o nosso pivô) com os indices do começo, meio e fim do vetor original e o pivo sera o elemento que for igual ao elemento do meio do auxiliar.
		if(minMedMax[1]==arr[ini])
			pivotIndex = ini;
		else if(minMedMax[1]==arr[fim])
			pivotIndex = fim;
		else
			pivotIndex = med;
		
		trocaValores(arr,pivotIndex,fim);
		return particionaQuickSort(arr,ini,fim);
	}
	// calcula o valor do pivô com base no algoritmo de mediana de 3
	public static int getPivotMedDe3Index(String arr[],int ini,int fim){
		int pivotIndex,mediana,max,med,min;
		int arrSize = fim;
		pivotIndex = 0;
		max = med = min = ini;
		
		// acha o elemento do meio do vetor
		if(arrSize%2==0)
			med = (arrSize/2)-1;
		else
			med = arrSize/2;
		// cria um vetor auxiliar com os valores do comeco, meio e fim do vetor
		String minMedMax[] = {arr[ini], arr[med], arr[fim]};

		for(int i=0;i<3;i++){ // faz a ordenacao desse vetor auxiliar, e por consequencia o elemento do meio sera o pivô desejado
			if(!compareStr(minMedMax[0],minMedMax[i]))
				trocaValores(minMedMax,0,i);
			if(compareStr(minMedMax[2],minMedMax[i]))
				trocaValores(minMedMax,2,i);
		}
		// busca comparando o elemento do meio do vetor auxiliar (o nosso pivô) com os indices do começo, meio e fim do vetor original e o pivo sera o elemento que for igual ao elemento do meio do auxiliar.
		if(minMedMax[1].equals(arr[ini]))
			pivotIndex = ini;
		else if(minMedMax[1].equals(arr[fim]))
			pivotIndex = fim;
		else
			pivotIndex = med;
		
		trocaValores(arr,pivotIndex,fim);
		return particionaQuickSort(arr,ini,fim);
	}

	// aplica o algoritmo de QuickSort com Mediana de 3 para inteiros
	public static void quickSortMediana3Code(int arr[]) {
		int pivo = getPivotMedDe3Index(arr,0,arr.length-1); // calcula o pivo da mediana de 3
		quickAndSort(arr,0,arr.length-1); // aplica o algoritmo de quickSort 
	}
	// aplica o algoritmo de QuickSort com Mediana de 3 para Strings
	public static void quickSortMediana3Code(String arr[]) {
		int pivo = getPivotMedDe3Index(arr,0,arr.length-1); // calcula o pivo da mediana de 3
		quickAndSort(arr,0,arr.length-1); // aplica o algoritmo de quickSort 
	}

	// aplica o algoritmo Counting Sort
	public static void countingSortCode(int vet[]){
		int maxElem,cont_aux,vet_aux[];
		cont_aux = 0;
		maxElem = vet[0]; // maior valor do vetor

		for(int elem: vet){ // busca o maior valor do vetor de dados
			if(maxElem<elem)
				maxElem = elem;
		}
		// cria um array auxiliar do tamanho do maior elemento do array
		vet_aux = new int[maxElem+1];

		for(int i=0;i<vet.length;i++){ // pega a quantidade de elementos que cada valor do vetor aparece e armazena essa contagem no index desse valor no vetor auxiliar 
			vet_aux[vet[i]] += 1;
		}
				
		for(int i=0;i<vet_aux.length;i++){ // percorre os elementos do vetor auxiliar
			if(vet_aux[i]!=0){ // se o elemento do vetor auxiliar não for 0, quer dizer que ele esta incluido no vetor original
				for(int j=0; j<vet_aux[i]; j++){ // percorre a quantidade de vezes que o elemento aparece no vetor
					vet[cont_aux++] = i; // armazena cada elemento dele (cada vez que ele aparece) no vetor original, apartir do index 0
				}
			}
		}	
	}

	public static void insertionSortCode(int arr[]){
        for(int i=1;i<arr.length;i++) {
            int elem = arr[i];
            int j = i-1;

            while(j>=0 && arr[j]>elem) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = elem;
        }
    }
	public static void insertionSortCode(String arr[]){
        for(int i=1;i<arr.length;i++) {
            String elem = arr[i];
            int j = i-1;

            while(j>=0 && validStrings(arr[j],elem) && !compareStr(arr[j],elem)) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = elem;
        }
    }

	// cria a Heap de inteiros para o algoritmo HeapSort
	public static void createHeap(int arr[],int arrSize,int index){
		int maxElem,left,right;
		maxElem = index;
		left = 2*index+1;
		right = 2*index+2;

		if(left<arrSize && arr[maxElem] < arr[left])
			maxElem = left;
		if(right<arrSize && arr[maxElem] < arr[right])
			maxElem = right;

		if(maxElem!=index){
			trocaValores(arr,index,maxElem);
			createHeap(arr,arrSize,maxElem);
		}	
	}
	// cria a Heap de String para o algoritmo HeapSort
	public static void createHeap(String arr[],int arrSize,int index){
		int maxElem,left,right;
		maxElem = index;
		left = 2*index+1;
		right = 2*index+2;
	
		if(left<arrSize && validStrings(arr[maxElem],arr[left]) && compareStr(arr[maxElem],arr[left]))
			maxElem = left;
		if(right<arrSize && validStrings(arr[maxElem],arr[right]) && compareStr(arr[maxElem],arr[right]))
			maxElem = right;

		if(maxElem!=index){
			trocaValores(arr,index,maxElem);
			createHeap(arr,arrSize,maxElem);
		}	
	}

	// aplicação do algoritmo HeapSort para inteiros
	public static void heapSortCode(int arr[]){
		int arrSize = arr.length;

		for(int i=(arrSize/2)-1; i>-1; i--){
			createHeap(arr,arrSize,i);
		}
		for(int i=arrSize-1; i > 0; i--){
			trocaValores(arr,i,0);
			createHeap(arr,i,0);
		}
	}

	// aplicação do algoritmo HeapSort para Strings
	public static void heapSortCode(String arr[]){
		int arrSize = arr.length;

		for(int i=(arrSize/2)-1; i>-1; i--){
			createHeap(arr,arrSize,i);
		}
		for(int i=arrSize-1; i > 0; i--){
			trocaValores(arr,i,0);
			createHeap(arr,i,0);
		}
	}

	// faz a ordenacao SelectionSort para inteiros
	public static void selectionSortCode(int numeros[]){
		int arraySize = numeros.length;

		for(int rodada=0;rodada<arraySize-1;rodada++){
			int menorPos = rodada; // a menor posicao do array começa sendo a primeira, para poder comparar com as proximas
			
			for(int i=rodada+1;i<arraySize;i++){
				if(numeros[i]<numeros[menorPos])
					menorPos = i;
			}
			// troca a menor valor do array encontrado na rodada pelo correspondente 	
			trocaValores(numeros,rodada,menorPos);
		}
	}
	// faz a ordenacao selection para Strings
	public static void selectionSortCode(String numeros[]){
		int arraySize = numeros.length;

		for(int rodada=0;rodada<arraySize-1;rodada++){
			int menorPos = rodada; // a menor posicao do array começa sendo a primeira, para poder comparar com as proximas
			
			for(int i=rodada+1;i<arraySize;i++){
				if(compareStr(numeros[i],numeros[menorPos]))
					menorPos = i;
			}
			// troca a menor valor do array encontrado na rodada pelo correspondente 	
			trocaValores(numeros,rodada,menorPos);
		}
	}
}