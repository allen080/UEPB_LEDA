import os

os.system("echo [*] Compilando...");
os.system("if not exist bin md bin");
os.system("javac -d bin src/*.java && cd bin && cls && java Main");

os.system("pause");