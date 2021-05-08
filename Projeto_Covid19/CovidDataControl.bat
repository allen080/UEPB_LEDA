@echo off
cls

javac -version 2> nul > nul && java -version 2> nul > nul && (
	color f
	echo [*] Compilando...
	if not exist bin md bin
	javac -d bin src/*.java && cd bin && cls && java Main
) || (
	color c
	echo [!] java nao encontrado. Verifique se o Java esta instalado corretamente no seu sistema.
)
echo.
pause
