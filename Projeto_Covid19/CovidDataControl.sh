#!/bin/bash

function pauseProg(){ echo -e "Pressione qualquer tecla para finalizar...\n" && read; }

javac -version 2> /dev/null > /dev/null && java -version 2> nul > nul

if [ $? -ne 0 ]; then
	echo [!] java.exe nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema.
	pauseProg
	exit 1
fi

echo [*] Compilando...
mkdir -p bin
javac -d bin src/*.java && cd bin && clear && java Main
pauseProg