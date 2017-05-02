/************************************
    ACH2024 - Algoritmos e Estruturas de Dados II

    Implementacao de Grafos utilizando Listas de Adjacencia
    (vetor de ponteiros no qual cada posicao indexa um vertice e
     contem o ponteiro para a cabeca de sua lista de adjacencia)

*************************************/

#include <stdbool.h>   /* variaveis bool assumem valores "true" ou "false" */
#include <stdint.h>    /* cast para converter ponteiros para int e vice-versa */

#define COR_BRANCO  0
#define COR_CINZA   1
#define COR_PRETO   2
#define INFINITO    9999

typedef int TipoPeso;
typedef int TipoCor;

/*
  tipo estruturado taresta:
      vertice destino, peso, ponteiro p/ prox. aresta
*/
typedef struct taresta {
  int vdest;
  TipoPeso peso;
  struct taresta *prox;
} TipoAresta;

typedef TipoAresta* TipoApontador;

/*
  tipo estruturado grafo:
      vetor de listas de adjacencia (cada posicao contem o ponteiro
                    para o inicio da lista de adjacencia do vertice)
      numero de vertices
*/
typedef struct {
  TipoApontador *listaAdj;
  int numVertices;
  int numArestas;
} TipoGrafo;

typedef struct tno {
  int v;
  struct tno *prox;
} TipoNo;

typedef struct {
  TipoNo *inicio;
  TipoNo *fim;
} TipoLista;

/********************

  Prototipos dos metodos sobre grafos

*********************/

/*
  inicializaGrafo(TipoGrafo* grafo, int nv): Cria um grafo com n vertices.
  Aloca espaco para o vetor de apontadores de listas de adjacencias e,
  para cada vertice, inicializa o apontador de sua lista de adjacencia.
  Retorna true se inicializou com sucesso e false caso contrario.
  Vertices vao de 1 a nv.
*/
bool inicializaGrafo(TipoGrafo *grafo, int nv);

/*
  void insereAresta(int v1, int v2, TipoPeso peso, TipoGrafo *grafo):
  Insere a aresta (v1, v2) com peso "peso" no grafo.
  Nao verifica se a aresta ja existe.
*/
void insereAresta(int v1, int v2, TipoPeso peso, TipoGrafo *grafo);

/*
  bool existeAresta(int v1, int v2, TipoGrafo *grafo):
  Retorna true se existe a aresta (v1, v2) no grafo e false caso contrário
*/
bool existeAresta(int v1, int v2, TipoGrafo *grafo);

/*
  bool removeAresta(int v1, int v2, TipoPeso* peso, TipoGrafo *grafo);
  Remove a aresta (v1, v2) do grafo.
  Se a aresta existia, coloca o peso dessa aresta em "peso" e retorna true,
  caso contrario retorna false (e "peso" é inalterado).
*/
bool removeAresta(int v1, int v2, TipoPeso* peso, TipoGrafo *grafo);

/*
   bool listaAdjVazia(int v, TipoGrafo* grafo):
   Retorna true se a lista de adjacencia (de vertices adjacentes) do vertice v é vazia, e false caso contrário.
*/
bool listaAdjVazia(int v, TipoGrafo *grafo);

/*
   TipoApontador primeiroListaAdj(int v, TipoGrafo* grafo):
   Retorna o endereco do primeiro vertice da lista de adjacencia de v
   ou NULL se a lista de adjacencia estiver vazia.
*/
TipoApontador primeiroListaAdj(int v, TipoGrafo *grafo);

/*
   TipoApontador proxListaAdj(int v, TipoGrafo* grafo):
   Retorna o proximo vertice adjacente a v, partindo do vertice "prox" adjacente a v
   ou NULL se a lista de adjacencia tiver terminado sem um novo proximo.
*/
TipoApontador proxListaAdj(int v, TipoGrafo *grafo, TipoApontador prox);

/*
    void imprimeGrafo(TipoGrafo* grafo):
    Imprime os vertices e arestas do grafo no seguinte formato:
    v1: (adj11, peso11); (adj12, peso12); ...
    v2: (adj21, peso21); (adj22, peso22); ...
    Assuma que cada vértice é um inteiro de até 2 dígitos.
*/
void imprimeGrafo(TipoGrafo *grafo);

/*
  void liberaGrafo (TipoGrafo *grafo): Libera o espaco ocupado por um grafo.
*/
void liberaGrafo (TipoGrafo *grafo);

/*
  LeGrafo(nomearq, Grafo)
  Le o arquivo nomearq e armazena na estrutura Grafo
  Lay-out:
    A 1a linha deve conter o número de vertices e o numero de arestas do grafo,
    separados por espaço.
    A 2a linha em diante deve conter a informacao de cada aresta, que consiste
    no indice do vertice de origem, indice do vertice de destino e o peso da
    aresta, tambem separados por espacos.
    Observações:
      Os vertices devem ser indexados de 0 a |V|-1
      Os pesos das arestas sao numeros racionais nao negativos.

  Exemplo: O arquivo abaixo contem um grafo com 4 verticennnns (0,1,2,3) e
  7 arestas.

  4 7
  0 3 6.3
  2 1 5.0
  2 0 9
  1 3 1.7
  0 1 9
  3 1 5.6
  0 2 7.2

  Codigo de saida:
    1: leitura bem sucedida
    0: erro na leitura do arquivo
*/
int leGrafo(char* nomearq, TipoGrafo *grafo);

/*
  fimListaAdj(v, p, Grafo): indica se o ponteiro atual p  chegou ao
        fim da lista de adjacencia de v (p == NULL).
*/
bool fimListaAdj(int v, TipoApontador p, TipoGrafo *grafo);

/*
  recuperaAdj(v, p, u, peso, grafo): dado um vertice v e um
      ponteiro para uma aresta da lista de adjacencia de v,
      devolve nas variaveis "peso" e "u" respectivamente o peso
      da aresta e o numero do vertice adjacente
*/
void recuperaAdj(int v, TipoApontador p, int *u, TipoPeso *peso,
                 TipoGrafo *grafo);

/*
  TipoApontador existeERetornaAresta(int v1, int v2, TipoGrafo *grafo):
  Retorna um apontador para a aresta (v1,v2) se ela existir e NULL caso Contrario.
*/
TipoApontador existeERetornaAresta(int v1, int v2, TipoGrafo *grafo);

/*
  TipoLista criaLista(): cria uma lista ligada vazia para inteiros
*/
TipoLista *criaLista();

/*
  insereFila(int v, TipoLista *fila): insere um elemento na fila.
*/
void insereFila(int v, TipoLista *fila);

/*
  int removeFila(TipoLista *fila): remove um elemento da fila.
*/
int removeFila(TipoLista *fila);

/*
  bool filaVazia(TipoLista *fila): verifica se a fila está vazia
*/
bool filaVazia(TipoLista *fila);

/*
  TipoNo *ultimoNo(TipoLista *fila): percorre a fila até o último nó
*/
TipoNo *ultimoNo(TipoLista *fila);

/*
  void imprimeFila(TipoLista *fila): imprime os elementos da fila
*/
void imprimeFila(TipoLista *fila);

/*
  inserePilha(int v, TipoLista *pilha): insere um elemento na pilha.
*/
void inserePilha(int v, TipoLista *pilha);

/*
  bool pilhaVazia(TipoLista *pilha): verifica se a pilha está vazia
*/
bool pilhaVazia(TipoLista *pilha);

/*
  TipoNo *ultimoNo(TipoLista *pilha): percorre a pilha até o último nó
*/
TipoNo *topo(TipoLista *pilha);

/*
  void imprimePilha(TipoLista *pilha): imprime os elementos da pilha
*/
void imprimePilha(TipoLista *pilha);

/*
  BFS(int *antecessores, int *distancias, TipoGrafo *grafo, TipoLista *descobertas):
  Executa a busca em largura no grafo
*/
void BFS(int *antecessores, int *distancias, TipoGrafo *grafo, TipoLista *descobertas);

/*
  visitaBFS(int v, TipoGrafo *grafo, TipoCor *cores, int *antecessores, int *distancias, TipoLista *descobertas):
  visita cada elemento do grafo e dispara a busca por novos elementos, atribuindo as novas cores e distâncias
*/
void visitaBFS(int v, TipoGrafo *grafo, TipoCor *cores, int *antecessores, int *distancias, TipoLista *descobertas);

/*
  imprimeBFS(TipoGrafo *grafo): imprime os dados de descobertas e caminhos do grafo
*/
void imprimeBFS(TipoGrafo *grafo);

/*
  imprimeCaminho(int v, int *antecessores): imprime um caminho de um vértice até sua raiz segundo a busca realizada
*/
void imprimeCaminho(int v, int *antecessores);

/*
  DFS(int *antecessores, int *distancias, TipoGrafo *grafo, TipoLista *descobertas):
  Executa a busca em profundidade no grafo
*/
void DFS(int *antecessores, TipoGrafo *grafo, TipoLista *descobertas);

/*
  visitaDFS(int v, TipoGrafo *grafo, TipoCor *cores, int *antecessores, int *distancias, TipoLista *descobertas):
  visita cada elemento do grafo e dispara a busca por novos elementos, atribuindo as novas cores e distâncias
*/
void visitaDFS(int v, TipoGrafo *grafo, int *tempo, TipoCor *cores, int *tdesc, int *tterm, int *antecessores, TipoLista *descobertas);

/*
  imprimeDFS(TipoGrafo *grafo): imprime os dados de descobertas e caminhos do grafo
*/
void imprimeDFS(TipoGrafo *grafo);

/*
  imprimeConjuntosConectados(TipoGrafo *grafo): imprime os conjuntos conectados encontrados no grafo
*/
void imprimeConjuntosConectados(TipoGrafo *grafo);

/*
  visitaConjuntosConectados(int v, TipoGrafo *grafo, TipoCor *cores, TipoLista *fila):
  percorre o grafo encontrando elementos para os conjuntos conectados
*/
void visitaConjuntosConectados(int v, TipoGrafo *grafo, TipoCor *cores, TipoLista *fila);
