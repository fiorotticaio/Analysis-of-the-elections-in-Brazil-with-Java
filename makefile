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

build:
	@ ant compile
	@ ant jar
	@ java -jar deputados.jar --federal consulta_cand_2022_ES.csv votacao_secao_2022_ES.csv 02/10/2022

compare:
	java -jar deputados.jar --estadual ../../input_candidatos/consulta_cand_2022_${UF}.csv ../../input_votacoes/votacao_secao_2022_${UF}.csv 02/10/2022

clear:
	@ clear