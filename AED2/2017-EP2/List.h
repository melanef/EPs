/* Lists.h */
#include <stdio.h>
#include <stdlib.h>

#ifndef NEF_LISTS
#define NEF_LISTS 1

typedef struct node {
    int value;
    struct node * next;
} Node;

typedef struct list {
    Node * head;
    Node * tail;
    int quantity;
} List;

List * listNew();

Node * nodeNew(int value);

Node * nodeFind(List * list, int value);

void listInsert(List * list, int value);

void listPrint(List * list);

#endif
