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

r = system("javac -version 2> {0} > {0} && java -version 2> {0} > {0}".format(trash));	

if r!=0:
	system("echo [!] java nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema.");
	input("\nPressione qualquer tecla para finalizar...")
	exit(1)

system("echo [*] Compilando...");

if not path.isdir("bin"):
	mkdir("bin")

system("javac -d bin src/*.java && cd bin && {} && java Main".format(clear));

input("\nPressione qualquer tecla para finalizar...")