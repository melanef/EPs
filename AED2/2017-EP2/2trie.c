/* TRIE */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include "List.h"
#include "String.h"
#include "Trie.h"

int main(int argc, const char ** argv)
{
    int i, quantity = 0;
    char textInput[129];
    char wordInput[129];

    int read = scanf("%d\n", &quantity);

    if (!read) {
        return 1;
    }

    for (i = 0; i < quantity; i++) {
        String * text = constructString(fgets(textInput, sizeof(textInput), stdin), sizeof(textInput));
        scanf("\n");
        String * word = constructString(fgets(wordInput, sizeof(wordInput), stdin), sizeof(wordInput));

        Trie * searchTrie = trieBuildFromText(text);

        List * found = trieSearch(searchTrie, word);

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
    }

    return 0;
}
