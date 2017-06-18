/* Trie.c */
#include "String.h"

Trie * trieNew()
{
    Trie * trie = (Trie *) malloc(sizeof(Trie));
    trie->sub = newList();

    return trie;
}

TrieNode * trieNodeNew(char c)
{
    TrieNode * node = (TrieNode *) malloc(sizeof(TrieNode));
    node->sub = newList();
    node->positions = newList();
    node->c = c;
}

void trieInsert(Trie * trie, String * word, int position)
{
    List * sub = trie->sub;
    TrieNode * node = NULL;

    for (int i = 0; i < word->length; i++) {
        node = trieFindOrCreateNode(sub, charAt(word, i));
        sub = node->sub;
    }

    listInsert(node->positions, position);
}

TrieNode * trieFindNode(List * sub, char x)
{
    Node * currentNode = sub->head;
    while (currentNode != NULL) {
        TrieNode * currentTrieNode = (TrieNode *) currentNode->value;
        if (currentTrieNode->c == x) {
            return currentTrieNode;
        }

        currentNode = currentNode->next;
    }

    return NULL;
}

TrieNode * trieFindOrCreateNode(List * sub, char x)
{
    TrieNode * found = trieFindNode(sub, x);
    if (found != NULL) {
        return found;
    }

    TrieNode * new = trieNodeNew(x);
    listInsert(sub, (uintptr_t) new);

    return new;
}

List * trieSearch(Trie * trie, String * word)
{
    List * sub = trie->sub;
    TrieNode * node = NULL;

    for (int i = 0; i < word->length; i++) {
        node = trieFindNode(sub, charAt(word, i));

        if (node == NULL) {
            return NULL;
        }

        sub = node->sub;
    }

    return node->positions;
}
