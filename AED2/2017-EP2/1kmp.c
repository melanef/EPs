/* KMP */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include "List.h"
#include "String.h"
#include "KMP.h"

int main(int argc, const char ** argv)
{
    int wordQuantity = 0, i;
    char textInput[10001];
    char wordInput[51];

    String * text = constructString(fgets(textInput, sizeof(textInput), stdin), sizeof(textInput));

    int read = scanf("%d\n", &wordQuantity);

    if (!read) {
        return 1;
    }

    List * wordList = listNew();
    for (i = 0; i < wordQuantity; i++) {
        if (scanf("%s", wordInput)) {
            String * word = constructString(wordInput, sizeof(wordInput));
            listInsert(wordList, (uintptr_t) word);
        }
    }

    Node * currentNode = wordList->head;
    while (currentNode != NULL) {
        String * currentWord = (String *) (uintptr_t) currentNode->value;

        List * found = KMPMatch(text, currentWord);

        if (found == NULL) {
            printf("-1\n");
        }

        if (found != NULL) {
            if (found->quantity) {
                listPrint(found);
            } else {
                printf("-1\n");
            }
        }

        currentNode = currentNode->next;
    }
    return 0;
}
