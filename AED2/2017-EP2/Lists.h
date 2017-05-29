# Lists.h
#include <stdio.h>

typedef struct CharList {
    CharNode head;
    CharNode tail;
    int quantity;
} CharList;

typedef struct CharNode {
    char valor;
    CharNode prox;
} CharNode;


