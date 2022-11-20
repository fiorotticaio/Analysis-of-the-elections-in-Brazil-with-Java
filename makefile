all: clear compile run

compile:
	@ javac -d bin src/*.java src/**/*.java


diff: compile
	@ echo "Compilando e rodando o programa..."
	@ java -cp bin App > src/testes/outputRelatorio$(RELAT).txt
	@ echo "Rodando diff..."
	@ diff ./src/testes/gabaritoRelatorio$(RELAT).txt ./src/testes/outputRelatorio$(RELAT).txt

run:
	@ java -cp bin App

clear:
	clear