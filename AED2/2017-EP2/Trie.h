/* Trie.h */

#include <stdint.h>         /* cast para converter ponteiros para int e vice-versa */
#include <stdbool.h>
#include "List.h"
#include "String.h"

#ifndef NEF_TRIE
#define NEF_TRIE 1

typedef struct trie {
    List * sub;
} Trie;

typedef struct trieNode {
    char c;
    List * sub;
    List * positions;
} TrieNode;


Trie * trieNew();

Trie * trieBuildFromText(String * text);

TrieNode * trieNodeNew(char c);

void trieInsert(Trie * trie, String * word, int position);

TrieNode * trieFindNode(List * sub, char x);

TrieNode * trieFindOrCreateNode(List * sub, char x);

List * trieSearch(Trie * trie, String * word);

void trieNodePrint(TrieNode * node);

#endif
