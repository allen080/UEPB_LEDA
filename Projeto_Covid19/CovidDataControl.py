from os import system

r = system("javac -version 2> nul > nul && java -version 2> nul > nul");	
if r!=0:
	system("color c && echo [!] java.exe nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema. && echo. && pause");
	exit(1)

system("color f");		
system("echo [*] Compilando...");
system("if not exist bin md bin");
system("javac -d bin src/*.java && cd bin && cls && java Main");

system("pause");