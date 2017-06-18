/* Trie.h */

#include <stdint.h>         /* cast para converter ponteiros para int e vice-versa */
#include "./List.h"
#include "./String.h"

typedef struct trie {
    List sub;
} Trie;

typedef struct trieNode {
    char c;
    List sub;
    List positions;
} TrieNode;


Trie * trieNew();

TrieNode * trieNodeNew(char c);

void trieInsert(Trie * trie, String * word, int position);

TrieNode * trieFindNode(List * sub, char x);

TrieNode * trieFindOrCreateNode(List * sub, char x);

List * trieSearch(Trie * trie, String * word);

