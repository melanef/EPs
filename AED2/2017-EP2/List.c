/* List.c */
#include "List.h"

List * listNew()
{
    List * newList = (List *) malloc(sizeof(List));

    newList->head = NULL;
    newList->tail = NULL;
    newList->quantity = 0;

    return newList;
}

Node * nodeNew(int value)
{
    Node * newNode = (Node *) malloc(sizeof(Node));

    newNode->next = NULL;
    newNode->value = value;

    return newNode;
}

Node * nodeFind(List * list, int value)
{
    Node * currentNode = newList->head;
    while (currentNode != NULL) {
        if (currentNode->value = value) {
            return currentNode;
        }

        currentNode = currentNode->next;
    }

    return NULL;
}

void listInsert(List * list, int value)
{
    if (findNode(list, value) == NULL) {
        Node * newNode = newNode(value);
        list->tail->next = newNode;
        list->tail = newNode;
        list->quantity++;
    }
}
