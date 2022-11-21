all: clear compile run

compile:
	@ javac -d bin src/*.java src/**/*.java


diff: compile
	@ echo "Compilando e rodando o programa..."
	@ java -cp bin App > src/gabarito/outputRelatorio$(RELAT).txt
	@ echo "Rodando diff..."
	@ diff ./src/gabarito/gabaritoRelatorio$(RELAT).txt ./src/gabarito/outputRelatorio$(RELAT).txt

run:
	@ java -cp bin App

clear:
	clear