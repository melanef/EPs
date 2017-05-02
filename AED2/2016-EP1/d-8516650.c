#include <stdio.h>
#include <stdlib.h>

#define INFINITE            999
#define TRUE                1
#define FALSE               0
#define UNDEFINED           -1

typedef int VerticeValueType;

typedef struct graphVertice {
    VerticeValueType value;
    struct graphEdge *edges;
    int edgeCount;
} graphVertice;

typedef struct graphEdge {
    graphVertice *vertice;
    int cost;
} graphEdge;

typedef struct graph {
    graphVertice *vertices;
    int verticeCount;
    int edgeCount;
} graph;

/**
 * parseia linha do arquivo de entrada e retorna array de inteiros com os
 * números encontrados na linha
 *
 * @param   char *  line    String com o conteúdo da linha
 * @return  int *   numbers Array de números encontrados, primeira posição
 *                          indica quantidade de números encontrados, demais
 *                          posições são os números
 */
int *readLine(char *line)
{
    int *numbers;
    int i, j, c;
    i = 0;
    c = 0;
    int status = FALSE;
    // Primeiro vamos descobrir quantos números existem nessa linha
    // Percorre caracter a caracter da linha
    while (line[i] != '\0') {
        if (line[i] >= '0' && line[i] <= '9') {
            // Se o caracter atual for um algarismo:
            //   Indicamos através de uma flag que há um número na posição atual
            status = TRUE;
        } else if (status) {
            // Se não:
            //   significa que estamos num separador
            //   Se a flag indicar a existência prévia de um número
            //     Incrementamos nosso contador (ou seja, passamos por um número
            //     e agora estamos num espaço)
            c++;
            //     Indica na flag que não há mais um número (já que o que havia
            //     foi contado já)
            status = FALSE;
        }
        // Avança pro próximo caractere
        i++;
    }

    // Pode ser que tenhamos chegado ao fim da linha tendo lido um número
    if (status) {
        // Se for o caso, incrementamos o contador
        c++;
        // e removemos a flag
        status = FALSE;
    }

    // Alocamos um array de inteiros com a quantidade de posições que descobrimos existir + 1
    numbers = (int *) malloc((c + 1) * sizeof(int));
    // Colocamos na primeira posição a quantidade de números encontrados
    numbers[0] = c;
    int number = 0;
    i = 0;
    j = 1;
    // Novamente, percorremos a linha
    while (line[i] != '\0') {
        if (line[i] >= '0' && line[i] <= '9') {
            // Se o caractere atual for um algarismo:
            //   Pode ser que seja um número de mais de um algarismo, portanto,
            //   vamos fazer com que o número que tinhamos até então avance uma
            //   casa decimal e então somamos o algarismo encontrado
            number = number * 10 + (line[i] - '0');
            //   Usamos nossa flag pra indicar que há um número encontrado
            status = TRUE;
        } else if (status) {
            // Se o caractere atual não for um algarismo:
            //   Se a flag de status indicar que encontramos um número antes:
            //     Guardamos nosso número encontrado no array (porque significa)
            //     que ele foi lido completamente
            numbers[j] = number;
            //     Zeramos a variável na qual estavamos armazenando o número
            number = 0;
            //     Avança a posição do array para o próximo número
            j++;
            //     Indica, pela flag, que não há mais número pendente
            status = FALSE;
        }
        // Avança pro próximo caractere
        i++;
    }

    // Pode ser que tenhamos chegado ao fim da linha com um número pendente
    if (status) {
        // Se for o caso, armazenamos o número no array
        numbers[j] = number;
    }

    // Retornamos nosso array
    return numbers;
}

/**
 * insere uma aresta
 * Inserir uma aresta consiste em re-alocar memória suficiente para as arestas
 * já existentes e mais a aresta que estamos incluindo, copiar as arestas para o
 * novo array com mais espaço, popular a aresta nova
 *
 * @param   int     sourceVerticeIndex          índice do vértice de origem
 * @param   int     destinationVerticeIndex     índice do vértice de destino
 * @param   int     cost                        custo da aresta
 * @param   graph * g                           grafo
 * @return  void
 */
void insertEdge(int sourceVerticeIndex, int destinationVerticeIndex, int cost, graph *g)
{
    int i;
    graphVertice *sourceVertice = &g->vertices[sourceVerticeIndex];
    graphVertice *destinationVertice = &g->vertices[destinationVerticeIndex];
    int newEdgeCount = sourceVertice->edgeCount + 1;
    int **links = NULL;

    if (sourceVertice->edgeCount > 0) {
        // Se houverem arestas neste vértice:
        //   Aloca um array bidimensional onde armazenaremos temporariamente os
        //   dados das arestas
        links = (int **) malloc(sourceVertice->edgeCount * sizeof(int *));
        // Para cada aresta do vértice
        for (i = 0; i < sourceVertice->edgeCount; i++) {
            // Aloca o array interno
            links[i] = (int *) malloc(2 * sizeof(int));
            // Popula: primeira posição é o endereço do vértice, segunda posição
            // é o custo da aresta
            links[i][0] = sourceVertice->edges[i].vertice->value;
            links[i][1] = sourceVertice->edges[i].cost;
        }

        // Copiamos todas as arestas, limpemos o array
        free(sourceVertice->edges);
    }

    // Agora sim, aloquemos o novo array de arestas com o novo tamanho*/
    sourceVertice->edges = (graphEdge *) malloc(newEdgeCount * sizeof(graphEdge));
    if (links && sourceVertice->edgeCount > 0) {
        // Se haviam arestas neste vértice:
        //   Para cada aresta representada em links
        for (i = 0; i < sourceVertice->edgeCount; i++) {
            // Popula a aresta com os dados armazenados em links[i]
            sourceVertice->edges[i].vertice = &g->vertices[links[i][0]];
            sourceVertice->edges[i].cost = links[i][1];
            // Se já copiamos os dados, podemos limpar esse elemento
            free(links[i]);
        }

        // Limpamos todos os elementos internos, agora podemos limpar o array
        free(links);
    }

    // Finalmente popula com a nova aresta
    sourceVertice->edges[newEdgeCount -1].vertice = destinationVertice;
    sourceVertice->edges[newEdgeCount -1].cost = cost;
    sourceVertice->edgeCount = newEdgeCount;
}

/**
 * imprime uma representação simples do grafo
 *
 * @param   graph * g   grafo
 * @return  void
 */
void printGraph(graph *g)
{
    int i, j;
    // Para cada vértice do grafo
    for (i = 0; i < g->verticeCount; i++) {
        graphVertice v = g->vertices[i];
        printf("Vertice #%d: %d edges\n", v.value, v.edgeCount);
        // Para cada aresta do vértice
        for (j = 0; j < v.edgeCount; j++) {
            printf(" -> %d (Cost: %d)\n", v.edges[j].vertice->value, v.edges[j].cost);
        }
    }
}

/**
 * "Construtor"
 * instancia grafo, vértices e os inicializa
 *
 * @param   int     verticeCount    Quantidade de vértices
 * @param   int     edgeCount       Quantidade de arestas
 * @return  graph * Grafo criado
 */
graph *newGraph(int verticeCount, int edgeCount)
{
    int i;
    // Alocamos memória para um grafo
    graph *g = (graph *) malloc(sizeof(graph));
    // Preenchemos as propriedades do grafo com os valores encontrados
    g->verticeCount = verticeCount;
    g->edgeCount = edgeCount;
    // Alocamos memória para os vértices
    g->vertices = (graphVertice *) malloc(verticeCount * sizeof(graphVertice));

    // Inicializamos cada vértice com seu valor e a contagem zerada de arestas
    for (i = 0; i < verticeCount; i++) {
        g->vertices[i].value = i;
        g->vertices[i].edgeCount = 0;
        g->vertices[i].edges = NULL;
    }

    return g;
}

/**
 * cria um grafo com base no arquivo
 * Espera-se que a primeira linha o arquivo tenha os totais de vértices e de
 * arestas. Demais linhas contem representações das arestas com o índice do
 * vértice de origem, o índice de destino e o custo da aresta.
 *
 * @param   char *  filename    String ocm o nome do arquivo de onde ler
 * @return  graph *             Grafo criado com os dados do arquivo
 */
graph *createGraphFromFile(const char *filename)
{
    int *lineNumbers;
    int i, n, m, o, d, c;
    char line[255];
    FILE *handler;
    graph *g;

    // Abrimos o arquivo
    handler = fopen(filename, "r");
    // Lemos a primeira linha do arquivo
    fgets(line, 255, handler);
    // Interpretamos a linha em busca dos números
    lineNumbers = readLine(line);
    if (lineNumbers[0] == 2) {
        // Se a linha tiver a quantidade esperada de números:
        //   guardamos valores encontrados
        n = lineNumbers[1];
        m = lineNumbers[2];
    } else {
        // Se não:
        //   Mensagem de erro e saímos da função
        printf("Entrada fora do padrao esperado. Encontrados %d numeros na primeira linha. Abortando execucao\n", lineNumbers[0]);
        return NULL;
    }

    // Invocamos o construtor do grafo
    g = newGraph(n, m);

    // Lemos as próximas linhas
    while (fgets(line, 255, handler) != NULL) {
        // Interpretamos a linha em busca dos números
        lineNumbers = readLine(line);
        if (lineNumbers[0] == 3) {
            // Se a linha tiver a quantidade esperada de números:
            //   guardamos os valores encontrados
            o = lineNumbers[1];
            d = lineNumbers[2];
            c = lineNumbers[3];

            // Inserimos a aresta com a configuração encontrada
            // Por se tratar de um grafo não direcionado, inserimos a aresta
            // tanto no sentido o -> d quanto d -> o
            insertEdge(o, d, c, g);
            insertEdge(d, o, c, g);
            // Atualiza contagem de arestas do grafo
            g->edgeCount++;
        }
    }

    fclose(handler);

    // Retorna o ponteiro para o grafo criado
    return g;
}

/**
 * verifica se o array de booleanos está vazio (todos os elementos = falso)
 *
 * @param   int *   booleanCollection   Array de booleanos
 * @param   int     size                Tamanho do array
 * @return  int     TRUE se houver algum elemento com valor TRUE, FALSE se não
 */
int notEmpty(int *booleanCollection, int size)
{
    int i;
    // Para cada elemento do array
    for (i = 0; i < size; i++) {
        if (booleanCollection[i] == TRUE) {
            // Se o valor do elemnto for true:
            //   retorna true
            return TRUE;
        }
    }

    // retorna false
    return FALSE;
}

/**
 * busca vértice de menor custo na lista de vértices que pertencem ao grafo
 * original mas não pertencem ao novo grafo
 *
 * @param   int *   gVerticesNotInS     Array de booleanos indicando quais
 *                                      vertices de G NÃO pertencem a S
 * @param   int *   verticesCosts       Array com os custos para se chegar a
 *                                      cada vértice
 * @param   int     size                Tamanho dos arrays
 * @return  int     Índice do vértice que tem menor custo calculado
 */
int findMinimumCostVerticeIndex(int *gVerticesNotInS, int *verticesCosts, int size)
{
    int i;
    // Inicializamos o menor custo como sendo infinito
    int minimum = INFINITE;
    // Inicializamos o índice do vértice de menor custo como indefinido
    int minimumIndex = UNDEFINED;
    // Para cada elemento dos arrays
    for (i = 0; i < size; i++) {
        if (gVerticesNotInS[i] && verticesCosts[i] < minimum) {
            // Se o vértice i de G não está em S e se o custo calculado para o
            // vértice i é menor do que o menor custo encontrado até então:
            //   Atualiza o menor custo com o valor encontrado e o índice
            minimum = verticesCosts[i];
            minimumIndex = i;
        }
    }

    // retorna o índice do vértice de menor custo
    return minimumIndex;
}

/**
 * Onde a mágica acontece:
 * Algoritmo de PRIM para obtenção de uma árvore geradora mínima
 *
 * @param   graph *     g   Grafo dado na entrada
 * @return  graph *     Grafo criado (árvore geradora mínima)
 */
graph *prim(graph *g)
{
    // Criamos um novo grafo com a quantidade de vértices do grafo dado porém 0
    // arestas
    graph *s = newGraph(g->verticeCount, 0);
    graphVertice *starter, *current, *analyzed;
    int i, analyzedCost;

    // Lista de vértices de G que não estão em S (um "array de booleanos"
    // indicando o estado de cada vértice)
    int *gVerticesNotInS = (int *) malloc(g->verticeCount * sizeof(int));
    // Lista de custos dos vértices
    int *verticesCosts = (int *) malloc(g->verticeCount * sizeof(int));
    // Lista dos elementos que levaram a cada vértice
    int *priorVertices = (int *) malloc(g->verticeCount * sizeof(int));

    // Para cada vértice de G
    for (i = 0; i < g->verticeCount; i++) {
        // Inicializa o custo como infinito
        verticesCosts[i] = INFINITE;
        // Inicializa o elemento anterior como indefinido
        priorVertices[i] = UNDEFINED;
        // Inicializa o vértice como não existindo em S
        gVerticesNotInS[i] = TRUE;
    }

    // Escolhendo o vértice 0 como inicial
    starter = &g->vertices[0];
    verticesCosts[0] = 0;

    // Enquanto houverem elementos de G que não existem em S
    while(notEmpty(gVerticesNotInS, g->verticeCount)) {
        // Buscamos o elemento de menor custo calculado
        int currentIndex = findMinimumCostVerticeIndex(gVerticesNotInS, verticesCosts, g->verticeCount);

        // Incluimos esse elemento em S
        // Como o vértice propriamente dito já existe em S, estamos, na verdade,
        // apenas marcando o vértice como existente em S na lista e criando a
        // aresta que liga este vértice ao seu anterior (se houver um anterior)
        gVerticesNotInS[currentIndex] = FALSE;
        if (priorVertices[currentIndex] != UNDEFINED) {
            // Se houver um vértice anterior que trouxe a este vértice atual
            //   Inserimos a aresta ligando o vértice anterior ao atual
            //   Por se tratar de um grafo não direcionado, inserimos a aresta
            //   tanto no sentido o -> d quanto d -> o
            insertEdge(priorVertices[currentIndex], currentIndex, verticesCosts[currentIndex], s);
            insertEdge(currentIndex, priorVertices[currentIndex], verticesCosts[currentIndex], s);
            // Atualiza contagem de arestas do grafo
            s->edgeCount++;
        }

        current = &g->vertices[currentIndex];
        // Para cada aresta do vértice atual
        for (i = 0; i < current->edgeCount; i++) {
            analyzedCost = current->edges[i].cost;
            // O vértice analizado é o vértice ao qual se chega pela aresta atual
            analyzed = current->edges[i].vertice;

            if (verticesCosts[analyzed->value] > analyzedCost) {
                // Se o custo armazenado for maior do que o custo apresentado
                // por esta aresta para chegar ao vértice analizado:
                //   atualiza o novo custo para o vértice analizado e indica o
                //   vértice que proporciona este caminho como o anterior a ele
                verticesCosts[analyzed->value] = analyzedCost;
                priorVertices[analyzed->value] = current->value;
            }
        }
    }

    // retorna novo grafo criado com todos os vértices porém com apenas as
    // arestas excenciais
    return s;
}

/**
 * Busca simples por um caminho entre vértices de índice X e Y
 *
 * @param   graph *     g           Grafo dado na entrada
 * @param   int         x           Índice do vértice de origem
 * @param   int         y           Índice do vértice de destino
 * @param   int *       visited     Lista de vértices visitados na busca
 * @return  int         TRUE se houver um caminho de X a Y, FALSE se não
 */
int searchPath(graph *g, int x, int y, int *visited)
{
    int i;
    graphVertice source = g->vertices[x];

    if (visited == NULL) {
        // Se a lista de vértices visitados for nula
        //   Cria uma lista de vértices
        visited = (int *) malloc(g->verticeCount * sizeof(int));
        //   Para cada vértice
        for (i = 0; i < g->verticeCount; i++) {
            // inicializa como "não visitado"
            visited[i] = FALSE;
        }
    }

    // Se o vértice atual foi visitado
    if (visited[x]) {
        // Retorna false pois significa que estamos dando voltas e não
        // encontramos um caminho para Y
        return FALSE;
    }

    // Marca o vértice atual como visitado
    visited[x] = TRUE;
    // Para cada aresta do vértice atual
    for (i = 0; i < source.edgeCount; i++) {
        if (source.edges[i].vertice->value == y) {
            // Se essa aresta levar a Y:
            //   retorna TRUE
            return TRUE;
        }
    }

    // Para cada aresta do vértice atual
    for (i = 0; i < source.edgeCount; i++) {
        if (searchPath(g, source.edges[i].vertice->value, y, visited)) {
            // Se houver caminho do vértice apontado por essa aresta até Y:
            //   retorna TRUE
            return TRUE;
        }
    }

    // retorna FALSE;
    return FALSE;
}

/**
 * Verifica se o grafo é conectado
 *
 * @param   graph *     g       Grafo dado na entrada
 * @return  int         TRUE se for conectado, FALSE se não
 */
int isGraphConnected(graph *g)
{
    int i;
    int result;
    // Para cada vértice de G exceto o primeiro
    for (i = 1; i < g->verticeCount; i++) {
        if (!searchPath(g, 0, i, NULL)) {
            // Se NÃO pudermos chegar ao vértice i através do vértice 0
            //   retorna FALSE pois indica que algum vértice é inalcansável
            return FALSE;
        }
    }

    // retorna TRUE
    return TRUE;
}

/**
 * Gera o arquivo de saída
 *
 * @param   graph *     g       Grafo construido com o algoritmo de PRIM
 * @return  int         TRUE em caso de sucesso, FALSE se não
 */
int generateOutput(graph *g, const char *filename)
{
    // Aloca um array bidimensional onde armazenaremos temporariamente os dados
    // das arestas
    int **links = (int **) malloc(g->edgeCount * sizeof(int *));
    int i, j, k, l, found;
    // Inicializa a soma dos custos com 0
    int sum = 0;
    FILE *handler;
    graphVertice currentVertice;
    graphEdge currentEdge;

    // Abrimos o arquivo
    handler = fopen(filename, "w");

    if (handler == NULL) {
        printf("O arquivo de saida nao existe ou nao tem permissao de escrita. Abortando execucao\n");
        return FALSE;
    }

    k = 0;
    // Para cada vértice
    for (i = 0; i < g->verticeCount; i++) {
        currentVertice = g->vertices[i];
        if (g->vertices[i].edgeCount) {
            // Se o vértice possui arestas:
            //   Para cada aresta
            for (j = 0; j < g->vertices[i].edgeCount; j++) {
                currentEdge = currentVertice.edges[j];
                // Criamos uma flag inicialmente como FALSE
                found = FALSE;
                // Para cada elemento do array de armazenamento das arestas
                for (l = 0; l < k; l++) {
                    if ((links[l][0] == currentVertice.value && links[l][1] == currentEdge.vertice->value) || (links[l][1] == currentVertice.value && links[l][0] == currentEdge.vertice->value)) {
                        // Se a aresta já tiver sido representada (o if admite
                        // duas possibilidades porque, sendo o grafo não-
                        // direcionado, representamos com ligações em ambos os
                        // vertices de origem e de destino):
                        //   Marcamos a flag como TRUE
                        found = TRUE;
                        break;
                    }
                }

                if (!found) {
                    // Se a flag nos indicar que a aresta NÃO foi encontrada
                    //   criamos um novo elemento no array de armazenamento de
                    //   arestas com os dados da aresta
                    links[k] = (int *) malloc(3 * sizeof(int));
                    links[k][0] = currentVertice.value;
                    links[k][1] = currentEdge.vertice->value;
                    links[k][2] = currentEdge.cost;
                    k++;
                }
            }
        }
    }

    // Para cada elemento do array de armazenamento de arestas
    for (i = 0; i < k; i++) {
        // Soma o valor do custo da aresta
        sum += links[i][2];
    }

    // Escreve, na primeira linha do arquivo, o total da soma da árvore
    fprintf(handler, "%d\n", sum);

    // Para cada elemento do array de armazenamento de arestas
    for (i = 0; i < k; i++) {
        // Escreve uma linha no arquivo de saída com a descrição da aresta
        fprintf(handler, "%d %d %d\n", links[i][0], links[i][1], links[i][2]);
        // Já usamos este dado, podemos limpar a memória
        free(links[i]);
    }

    // Já limpamos todos os elementos do array bidimensional, podemos limpá-lo
    free(links);

    // Fecha o arquivo
    fclose(handler);

    return TRUE;
}

/**
 * De onde começa a rodar :)
 *
 * @param   int     argc    Contador de argumentos
 * @param   char *  argv    Array de Strings dos argumentos
 * @return  int     0 se sucesso ou código de erro se falhar
 */
int main(int argc, const char *argv[])
{
    graph *g, *s;

    if (argc != 3) {
        // Se a execução não proporciona os parâmetros esperados, emite erro e sai com código de erro
        printf("São esperados 2 argumentos para a execucao do programa: arquivografo e arquivosaida");
        return 1;
    }

    // Cria grafo a partir do arquivo de entrada
    g = createGraphFromFile(argv[1]);

    if (g == NULL) {
        // Se a criação do grafo falhou por algum motivo, sai com código de erro
        return 2;
    }

    if (!isGraphConnected(g)) {
        // Se o grafo não é conectado, emite erro e sai com código de erro,
        // porque essa é uma característica obrigatória para o funcionamento do
        // algoritmo de PRIM
        printf("O grafo nao e conectado. Abortando a execucao\n");
        return 3;
    }

    // Criamos o grafo S aplicando o algoritmo de PRIM ao grafo G
    s = prim(g);

    // Escreve a representação da saída em arquivo
    if (!generateOutput(s, argv[2])) {
        // Se a representação falhou, sai com código de erro
        return 4;
    }

    // Sai sem código de erro
    return 0;
}
