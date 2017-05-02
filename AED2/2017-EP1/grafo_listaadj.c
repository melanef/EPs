#include "./grafo_listaadj.h"

int leGrafo(char* nomearq, TipoGrafo *grafo)
{
    FILE *arquivo = fopen(nomearq, "r");
    if (!arquivo) {
        printf("Não foi possível abrir o arquivo '%s'. Certifique-se de que ele existe, possui permissão de leitura e foi informado corretaamente.\n", nomearq);
        return 0;
    }

    fscanf(arquivo, "%d %d", &(grafo->numVertices), &(grafo->numArestas));

    inicializaGrafo(grafo, grafo->numVertices);

    for (int i = 0; i < grafo->numArestas; i++) {
        int verticeOrigem, verticeDestino, peso;
        fscanf(arquivo, "%d %d %d", &verticeOrigem, &verticeDestino, &peso);
        if (!existeAresta(verticeOrigem, verticeDestino, grafo)) {
            insereAresta(verticeOrigem, verticeDestino, peso, grafo);
        }

        if (!existeAresta(verticeDestino, verticeOrigem, grafo)) {
            insereAresta(verticeDestino, verticeOrigem, peso, grafo);
        }
    }

    return 1;
}

bool inicializaGrafo(TipoGrafo *grafo, int nv)
{
    grafo->listaAdj = (TipoApontador *) malloc(nv * sizeof(TipoApontador));
    for (int i = 0; i < nv; i++) {
        grafo->listaAdj[i] = NULL;
    }

    return true;
}

void insereAresta(int v1, int v2, TipoPeso peso, TipoGrafo *grafo)
{
    TipoApontador aresta = (TipoApontador) malloc(sizeof(TipoAresta));
    aresta->vdest = v2;
    aresta->peso = peso;
    aresta->prox = NULL;

    if (listaAdjVazia(v1, grafo)) {
        grafo->listaAdj[v1 - 1] = aresta;
    } else {
        /*
        TipoApontador arestaAtual = grafo->listaAdj[v1 - 1];
        while (arestaAtual->prox != NULL) {
            arestaAtual = arestaAtual->prox;
        }

        arestaAtual->prox = aresta;
        /**/
        aresta->prox = grafo->listaAdj[v1 - 1];
        grafo->listaAdj[v1 - 1] = aresta;
        /**/
    }
}

bool existeAresta(int v1, int v2, TipoGrafo *grafo)
{
    if (listaAdjVazia(v1, grafo)) {
        return false;
    }

    TipoApontador arestaAtual = primeiroListaAdj(v1, grafo);
    while (arestaAtual->prox != NULL) {
        if (arestaAtual->vdest == v2) {
            return true;
        }

        arestaAtual = arestaAtual->prox;
    }

    return false;
}

bool removeAresta(int v1, int v2, TipoPeso *peso, TipoGrafo *grafo)
{
    if (listaAdjVazia(v1, grafo)) {
        return false;
    }

    TipoApontador arestaAtual = primeiroListaAdj(v1, grafo);
    TipoApontador arestaAnterior = NULL;
    while (arestaAtual->prox != NULL) {
        if (arestaAtual->vdest == v2) {
            *peso = arestaAtual->peso;

            arestaAnterior->prox = arestaAtual->prox;
            free(arestaAtual);

            return true;
        }

        arestaAnterior = arestaAtual;
        arestaAtual = arestaAtual->prox;
    }

    return false;
}

bool listaAdjVazia(int v, TipoGrafo *grafo)
{
    if (grafo->listaAdj[v - 1] == NULL) {
        return true;
    }

    return false;
}

TipoApontador primeiroListaAdj(int v, TipoGrafo *grafo)
{
    return grafo->listaAdj[v - 1];
}

TipoApontador proxListaAdj(int v, TipoGrafo *grafo, TipoApontador prox)
{
    return prox->prox;
}

void imprimeGrafo(TipoGrafo *grafo)
{
    int arestasImpressas[grafo->numVertices][grafo->numVertices];
    for (int i = 0; i < grafo->numVertices; i++) {
        for (int j = 0; j < grafo->numVertices; j++) {
            arestasImpressas[i][j] = 0;
        }
    }

    printf("%d %d\n", grafo->numVertices, grafo->numArestas);
    for (int i = 1; i <= grafo->numVertices; i++) {
        TipoApontador aresta = primeiroListaAdj(i, grafo);
        while (aresta != NULL) {
            if (!(arestasImpressas[i - 1][aresta->vdest - 1] && arestasImpressas[aresta->vdest - 1][i - 1])) {
                printf("%d %d %d\n", i, aresta->vdest, aresta->peso);
                arestasImpressas[i - 1][aresta->vdest - 1] = 1;
                arestasImpressas[aresta->vdest - 1][i - 1] = 1;
            }

            aresta = proxListaAdj(i, grafo, aresta);
        }
    }

    printf("\n");
}

void liberaGrafo (TipoGrafo *grafo)
{
    for (int i = 0; i < grafo->numVertices; i++) {
        TipoApontador aresta = grafo->listaAdj[i];
        TipoApontador proxAresta = NULL;
        while (aresta != NULL) {
            proxAresta = aresta->prox;
            free(aresta);
            aresta = proxAresta;
        }
    }

    free(grafo->listaAdj);
    free(grafo);
}

bool fimListaAdj(int v, TipoApontador p, TipoGrafo *grafo)
{
    if (p == NULL) {
        return true;
    }

    return false;
}

void recuperaAdj(int v, TipoApontador p, int *u, TipoPeso *peso, TipoGrafo *grafo)
{
    if (!fimListaAdj(v, p, grafo)) {
        *peso = p->peso;
        *u = p->vdest;
    }
}

TipoApontador existeERetornaAresta(int v1, int v2, TipoGrafo *grafo)
{
    if (listaAdjVazia(v1, grafo)) {
        return NULL;
    }

    TipoApontador arestaAtual = primeiroListaAdj(v1, grafo);
    while (arestaAtual->prox != NULL) {
        if (arestaAtual->vdest == v2) {
            return arestaAtual;
        }

        arestaAtual = arestaAtual->prox;
    }

    return NULL;
}

TipoLista *criaLista()
{
    TipoLista *fila = (TipoLista *) malloc(sizeof(TipoLista));
    fila->inicio = NULL;
    fila->fim = NULL;
    return fila;
}

TipoNo *ultimoNo(TipoLista *fila)
{
    if (listaVazia(fila)) {
        return NULL;
    }

    return fila->fim;
}

void insereOrdenado(int v, TipoLista *lista)
{
    TipoNo *novoNo = (TipoNo *) malloc(sizeof(TipoNo));
    novoNo->v = v;
    novoNo->prox = NULL;

    if (listaVazia(lista)) {
        lista->inicio = novoNo;
        lista->fim = novoNo;
    } else{
        bool inserido = false;
        TipoNo *atual = lista->inicio;

        if (atual->v > v) {
            lista->inicio = novoNo;
            novoNo->prox = atual;
        } else {
            while (atual->prox != NULL) {
                if (atual->prox->v > v) {
                    novoNo->prox = atual->prox;
                    atual->prox = novoNo;
                    inserido = true;
                    break;
                }

                atual = atual->prox;
            }

            if (!inserido) {
                atual->prox = novoNo;
            }
        }
    }
}

void insereFila(int v, TipoLista *fila)
{
    TipoNo *novoNo = (TipoNo *) malloc(sizeof(TipoNo));
    novoNo->v = v;
    novoNo->prox = NULL;

    if (listaVazia(fila)) {
        fila->inicio = novoNo;
        fila->fim = novoNo;
    } else {
        TipoNo *fim = ultimoNo(fila);
        fim->prox = novoNo;
        fila->fim = novoNo;
    }
}

int removeFila(TipoLista *fila)
{
    TipoNo *atual = fila->inicio;
    fila->inicio = atual->prox;

    if (fila->inicio == NULL) {
        fila->fim = NULL;
    }

    int valor = atual->v;
    free(atual);
    return valor;
}

bool listaVazia(TipoLista *lista)
{
    if (lista->inicio == NULL) {
        return true;
    }

    return false;
}

void imprimeLista(TipoLista *lista)
{
    TipoNo *atual = lista->inicio;
    while (atual != NULL) {
        printf("%d ", atual->v);
        atual = atual->prox;
    }
}

void inserePilha(int v, TipoLista *pilha)
{
    TipoNo *novoNo = (TipoNo *) malloc(sizeof(TipoNo));
    novoNo->v = v;
    novoNo->prox = NULL;

    if (listaVazia(pilha)) {
        pilha->inicio = novoNo;
        pilha->fim = novoNo;
    } else {
        TipoNo *topo = pilha->inicio;
        novoNo->prox = topo;
        pilha->inicio = novoNo;
    }
}

void BFS(int *antecessores, int *distancias, TipoGrafo *grafo, TipoLista *descobertas)
{
    TipoCor cores[grafo->numVertices];
    for (int i = 0; i < grafo->numVertices; i++) {
        cores[i] = COR_BRANCO;
        antecessores[i] = -1;
        distancias[i] = INFINITO;
    }

    for (int i = 0; i < grafo->numVertices; i++) {
        if (cores[i] == COR_BRANCO) {
            visitaBFS(i + 1, grafo, cores, antecessores, distancias, descobertas);
        }
    }
}

void visitaBFS(int v, TipoGrafo *grafo, TipoCor *cores, int *antecessores, int *distancias, TipoLista *descobertas)
{
    cores[v - 1] = COR_CINZA;
    insereFila(v, descobertas);

    distancias[v - 1] = 0;
    TipoLista *fila = criaLista();
    insereFila(v, fila);
    while (!listaVazia(fila)) {
        int w = removeFila(fila);
        if (!listaAdjVazia(w, grafo)) {
            TipoApontador aresta = primeiroListaAdj(w, grafo);
            while (aresta != NULL) {
                int u = aresta->vdest;
                if (cores[u - 1] == COR_BRANCO) {
                    cores[u - 1] = COR_CINZA;
                    insereFila(u, descobertas);
                    antecessores[u - 1] = w;
                    distancias[u - 1] = distancias[w - 1] + 1;
                    insereFila(u, fila);
                }

                aresta = aresta->prox;
            }
        }

        cores[w] = COR_PRETO;
    }
}

void imprimeBFS(TipoGrafo *grafo)
{
    int antecessores[grafo->numVertices];
    int distancias[grafo->numVertices];
    TipoLista *descobertas = criaLista();

    BFS(antecessores, distancias, grafo, descobertas);

    printf("BFS:\n");
    imprimeLista(descobertas);
    printf("\n\n");

    printf("BFS Paths:\n");
    for (int i = 0; i < grafo->numVertices; i++) {
        int atual = i + 1;
        /*
        TipoLista *caminho = criaLista();
        inserePilha(atual, caminho);
        while (antecessores[atual - 1] != -1) {
            atual = antecessores[atual - 1];
            inserePilha(atual, caminho);
        }

        imprimeLista(caminho);
        */
        imprimeCaminho(atual, antecessores);

        printf("\n");
    }

    printf("\n");
}

void imprimeCaminho(int v, int *antecessores)
{
    if (antecessores[v - 1] != -1) {
        imprimeCaminho(antecessores[v - 1], antecessores);
    }

    printf("%d ", v);
}

void DFS(int *antecessores, TipoGrafo *grafo, TipoLista *descobertas)
{
    int tdesc[grafo->numVertices], tterm[grafo->numVertices];
    int tempo = 0;
    TipoCor cores[grafo->numVertices];
    for (int i = 0; i < grafo->numVertices; i++) {
        cores[i] = COR_BRANCO;
        tdesc[i] = 0;
        tterm[i] = 0;
        antecessores[i] = -1;
    }

    for (int i = 0; i < grafo->numVertices; i++) {
        if (cores[i] == COR_BRANCO) {
            visitaDFS(i + 1, grafo, &tempo, cores, tdesc, tterm, antecessores, descobertas);
        }
    }
}

void visitaDFS(int v, TipoGrafo *grafo, int *tempo, TipoCor *cores, int *tdesc, int *tterm, int *antecessores, TipoLista *descobertas)
{
    *tempo = *tempo + 1;
    cores[v - 1] = COR_CINZA;
    tdesc[v - 1] = *tempo;
    insereFila(v, descobertas);
    if (!listaAdjVazia(v, grafo)) {
        TipoApontador aresta = primeiroListaAdj(v, grafo);
        while (aresta != NULL) {
            if (cores[aresta->vdest - 1] == COR_BRANCO) {
                antecessores[aresta->vdest - 1] = v;
                visitaDFS(aresta->vdest, grafo, tempo, cores, tdesc, tterm, antecessores, descobertas);
            }

            aresta = aresta->prox;
        }
    }

    *tempo = *tempo + 1;
    tdesc[v - 1] = *tempo;
    cores[v - 1] = COR_PRETO;
}

void imprimeDFS(TipoGrafo *grafo)
{
    int antecessores[grafo->numVertices];
    TipoLista *descobertas = criaLista();

    DFS(antecessores, grafo, descobertas);

    printf("DFS:\n");
    imprimeLista(descobertas);
    printf("\n\n");

    printf("DFS Paths:\n");
    for (int i = 0; i < grafo->numVertices; i++) {
        int atual = i + 1;
        /*
        TipoLista *caminho = criaLista();
        inserePilha(atual, caminho);
        while (antecessores[atual - 1] != -1) {
            atual = antecessores[atual - 1];
            inserePilha(atual, caminho);
        }

        imprimeLista(caminho);
        */
        imprimeCaminho(atual, antecessores);

        printf("\n");
    }

    printf("\n");
}

void imprimeConjuntosConectados(TipoGrafo *grafo)
{
    TipoCor cores[grafo->numVertices];
    for (int i = 0; i < grafo->numVertices; i++) {
        cores[i] = COR_BRANCO;
    }

    TipoLista *conjuntos = criaLista();

    for (int i = 0; i < grafo->numVertices; i++) {
        int atual = i + 1;
        if (cores[atual - 1] == COR_BRANCO) {
            TipoLista *fila = criaLista();
            visitaConjuntosConectados(atual, grafo, cores, fila);
            insereFila((uintptr_t) fila, conjuntos);
        }
    }

    printf("Connected Components:\n");
    int i = 0;
    while (!listaVazia(conjuntos)) {
        TipoLista *endereco = (TipoLista *) (uintptr_t) removeFila(conjuntos);
        printf("C%d: ", ++i);
        while (!listaVazia(endereco)) {
            printf("%d ", removeFila(endereco));
        }

        printf("\n");
    }
}

void visitaConjuntosConectados(int v, TipoGrafo *grafo, TipoCor *cores, TipoLista *fila) {
    if (cores[v - 1] == COR_BRANCO) {
        cores[v - 1] = COR_CINZA;
        insereOrdenado(v, fila);

        if (!listaAdjVazia(v, grafo)) {
            TipoApontador aresta = primeiroListaAdj(v, grafo);
            while (aresta != NULL) {
                visitaConjuntosConectados(aresta->vdest, grafo, cores, fila);
                aresta = aresta->prox;
            }
        }
    }
}
