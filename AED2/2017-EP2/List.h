/* Lists.h */
#include <stdio.h>

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

List * newList();

Node * newNode(int value);

Node * findNode(List * list, int value);

void insertList(List * list, int value);

#endif
