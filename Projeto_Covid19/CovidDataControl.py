from os import system,path,mkdir
from sys import platform

def getOS():
	if "win" in platform:
		return 1 # WINDOWS
	elif "linux" in platform: 
		return 2 # LINUX
	return 3 # OS X

sistema = getOS()

if sistema==1:
	trash = "nul"
	clear = "cls"
else:
	trash = "/dev/null"
	clear = "clear"

r = system(f"javac -version 2> {trash} > {trash} && java -version 2> {trash} > {trash}");	

if r!=0:
	system(f"echo [!] java nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema.");
	input("\nPressione qualquer tecla para finalizar...")
	exit(1)

system("echo [*] Compilando...");

if not path.isdir("bin"):
	mkdir("bin")

system(f"javac -d bin src/*.java && cd bin && {clear} && java Main");

input("\nPressione qualquer tecla para finalizar...")