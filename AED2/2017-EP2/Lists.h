/* Lists.h */
#include <stdio.h>

#ifndef NEF_LISTS
#define NEF_LISTS 1

typedef struct charnode {
    char value;
    struct charnode * prox;
} CharNode;

typedef struct charlist {
    CharNode * head;
    CharNode * tail;
    int quantity;
} CharList;



#endif
