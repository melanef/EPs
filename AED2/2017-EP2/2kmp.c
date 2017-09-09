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

        List * found = KMPMatch(text, word);

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
