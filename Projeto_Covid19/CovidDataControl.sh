#!/bin/bash

function pauseProg(){ read -rsp "Pressione qualquer tecla para finalizar..."; }

javac -version 2> nul > nul && java -version 2> nul > nul

if [ $? -ne 0 ]; then
	echo [!] java.exe nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema.
	pauseProg
	exit 1
fi

echo [*] Compilando...
mkdir -p bin
javac -d bin src/*.java && cd bin && clear && java Main
pauseProg