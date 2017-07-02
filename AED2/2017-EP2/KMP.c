/* KMP.c */

#include "KMP.h"

int * KMPFailureFunction(String * word)
{
    int * failureTable = (int *) malloc(word->length * sizeof(int));

    failureTable[0] = 0;

    int i = 1, j = 0;
    while (i < word->length) {
        if (charAt(word, i) == charAt(word, j)) {
            failureTable[i] = j + 1;
            i++;
            j++;
        } else if (j > 0) {
            j = failureTable[j - 1];
        } else {
            failureTable[i] = 0;
            i++;
        }
    }

    return failureTable;
}

List * KMPMatch(String * text, String * word)
{
    List * found = listNew();
    int * failureTable = KMPFailureFunction(word);
    int i = 0, j = 0;
    while (i < text->length) {
        if (charAt(text, i) == charAt(word, j)) {
            if (j == word->length - 1) {
                if ((i == (text->length - 1) || charAt(text, i + 1) == ' ') && (i == 0 || charAt(text, i - j - 1) == ' ')) {
                    listInsert(found, (i - j));
                }

                i++;
                j = 0;
            } else {
                i++;
                j++;
            }
        } else {
            if (j > 0) {
                j = failureTable[j - 1];
            } else {
                i++;
            }
        }
    }

    return found;
}
