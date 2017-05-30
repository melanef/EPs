/* Trie.h */

#include <stdint.h>         /* cast para converter ponteiros para int e vice-versa */
#include "./ListaLigada.h"
#include "./String.h"
#include "./Trie.c"

typedef struct TrieTree {
    ListaLigada sub;
    ListaLigada posicoes;
} TrieTree;

TrieTree *criaTrie();

void populaTrie(TrieTree trie, String string);

ListaLigada busca(TrieTree trie, String string);
