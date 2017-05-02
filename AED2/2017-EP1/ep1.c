#include <stdio.h>
#include <stdlib.h>
#include "./grafo_listaadj.c"


#define ERRROR_OPENFILE 1

int main(int argc, char *argv[])
{
	if (argc != 2) {
		printf("É necessário informar um, e apenas um parâmetro: endereço do arquivo de onde ler o grafo.\n");
        return EXIT_FAILURE;
	}

    TipoGrafo *grafo = (TipoGrafo *) malloc(sizeof(TipoGrafo));
    leGrafo(argv[1], grafo);
    imprimeGrafo(grafo);
    imprimeBFS(grafo);
    imprimeDFS(grafo);
    TipoLista *conjuntos = conjuntosConectados(grafo);
    imprimeConjuntosConectados(conjuntos);

    return EXIT_SUCCESS;
}

