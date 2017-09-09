#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <time.h>
#include "List.h"
#include "String.h"
#include "KMP.h"
#include "Trie.h"

void compare1();
void compare2();
void trie1(String * text, List * wordList);
void kmp1(String * text, List * wordList);
void trie2(String * text, String * word);
void kmp2(String * text, String * word);

int main(int argc, const char ** argv)
{
    compare1();
    printf("\n");
    compare2();
    printf("\n");
}

void compare1()
{
    clock_t begin, end;
    double trieTime, kmpTime;

    int wordQuantity = 0, i;
    char textInput[10001];
    char wordInput[51];

    FILE * file = fopen("./1.in", "r");

    String * text = constructString(fgets(textInput, sizeof(textInput), file), sizeof(textInput));
    if (fscanf(file, "%d\n", &wordQuantity)) {
        List * wordList = listNew();
        for (i = 0; i < wordQuantity; i++) {
            if (fscanf(file, "%s", wordInput)) {
                String * word = constructString(wordInput, sizeof(wordInput));
                listInsert(wordList, (uintptr_t) word);
            }
        }

        begin = clock();
        trie1(text, wordList);
        end = clock();
        trieTime = (double)(end - begin) / CLOCKS_PER_SEC;

        begin = clock();
        kmp1(text, wordList);
        end = clock();
        kmpTime = (double)(end - begin) / CLOCKS_PER_SEC;

        printf("%f %f\n", trieTime, kmpTime);
    }
}

void compare2()
{
    clock_t begin, end;
    double trieTime, kmpTime;

    int i, quantity = 0;
    char textInput[129];
    char wordInput[129];

    FILE * file = fopen("./2.in", "r");

    if (fscanf(file, "%d\n", &quantity)) {
        begin = clock();
        for (i = 0; i < quantity; i++) {
            String * text = constructString(fgets(textInput, sizeof(textInput), file), sizeof(textInput));
            fscanf(file, "\n");
            String * word = constructString(fgets(wordInput, sizeof(wordInput), file), sizeof(wordInput));

            trie2(text, word);
        }
        end = clock();
        trieTime = (double)(end - begin) / CLOCKS_PER_SEC;

        rewind(file);

        begin = clock();
        for (i = 0; i < quantity; i++) {
            String * text = constructString(fgets(textInput, sizeof(textInput), file), sizeof(textInput));
            fscanf(file, "\n");
            String * word = constructString(fgets(wordInput, sizeof(wordInput), file), sizeof(wordInput));

            kmp2(text, word);
        }
        end = clock();
        kmpTime = (double)(end - begin) / CLOCKS_PER_SEC;

        printf("%f %f\n", trieTime, kmpTime);
    }
}

void trie1(String * text, List * wordList)
{
    Trie * searchTrie = trieBuildFromText(text);

    Node * currentNode = wordList->head;
    while (currentNode != NULL) {
        String * currentWord = (String *) (uintptr_t) currentNode->value;
        List * found = trieSearch(searchTrie, currentWord);
        if (found == NULL) {
            //printf("-1\n");
        }

        if (found != NULL) {
            if (found->quantity) {
                //listPrint(found);
            } else {
                //printf("-1\n");
            }
        }

        currentNode = currentNode->next;
    }
}

void kmp1(String * text, List * wordList)
{
    Node * currentNode = wordList->head;
    while (currentNode != NULL) {
        String * currentWord = (String *) (uintptr_t) currentNode->value;

        List * found = KMPMatch(text, currentWord);

        if (found == NULL) {
            //printf("-1\n");
        }

        if (found != NULL) {
            if (found->quantity) {
                //listPrint(found);
            } else {
                //printf("-1\n");
            }
        }

        currentNode = currentNode->next;
    }
}

void trie2(String * text, String * word)
{
    Trie * searchTrie = trieBuildFromText(text);

    List * found = trieSearch(searchTrie, word);

    if (found == NULL) {
        //printf("-1\n");
    }

    if (found != NULL) {
        if (found->quantity) {
            //listPrint(found);
        } else {
            //printf("-1\n");
        }
    }
}

void kmp2(String * text, String * word)
{
    List * found = KMPMatch(text, word);

    if (found == NULL) {
        //printf("-1\n");
    }

    if (found != NULL) {
        if (found->quantity) {
            //listPrint(found);
        } else {
            //printf("-1\n");
        }
    }
}
