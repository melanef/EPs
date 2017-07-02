/* Trie.c */
#include "Trie.h"

Trie * trieNew()
{
    Trie * trie = (Trie *) malloc(sizeof(Trie));
    trie->sub = listNew();

    return trie;
}

Trie * trieBuildFromText(String * text)
{
    Trie * new = trieNew();

    int i, start = 0, end;
    bool found = false;
    for (i = 0; i < text->length; i++) {
        char c = charAt(text, i);
        if (c == ' ' || c == EOL || c == EOF || c == EOS) {
            end = i;

            if (end > start) {
                //printString(substringFromString(text, start, end));
                trieInsert(new, substringFromString(text, start, end), start);
            }

            start = i + 1;
        }
    }

    end = text->length;
    if (end > start) {
        //printString(substringFromString(text, start, end));
        trieInsert(new, substringFromString(text, start, end), start);
    }


    return new;
}

TrieNode * trieNodeNew(char c)
{
    TrieNode * node = (TrieNode *) malloc(sizeof(TrieNode));
    node->sub = listNew();
    node->positions = listNew();
    node->c = c;

    return node;
}

void trieInsert(Trie * trie, String * word, int position)
{
    List * sub = trie->sub;
    TrieNode * node = NULL;
    int i;

    for (i = 0; i < word->length - 1; i++) {
        node = trieFindOrCreateNode(sub, charAt(word, i));
        sub = node->sub;
    }

    listInsert(node->positions, position);
}

TrieNode * trieFindNode(List * sub, char x)
{
    Node * currentNode = sub->head;
    while (currentNode != NULL) {
        TrieNode * currentTrieNode = (TrieNode *) (uintptr_t) currentNode->value;
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
    int i;

    for (i = 0; i < word->length; i++) {
        node = trieFindNode(sub, charAt(word, i));

        if (node == NULL) {
            return NULL;
        }

        sub = node->sub;
    }

    return node->positions;
}

void trieNodePrint(TrieNode * node)
{
    printf("Char: %c\nPositions: ", node->c);
    listPrint(node->positions);
}
