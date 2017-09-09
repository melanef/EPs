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
    Node * currentNode = list->head;
    while (currentNode != NULL) {
        if (currentNode->value == value) {
            break;
        }

        currentNode = currentNode->next;
    }

    return currentNode;
}

void listInsert(List * list, int value)
{
    if (nodeFind(list, value) == NULL) {
        Node * newNode = nodeNew(value);
        if (list->tail != NULL) {
            list->tail->next = newNode;
        }

        list->tail = newNode;

        if (list->head == NULL) {
            list->head = newNode;
        }

        list->quantity++;
    }
}

void listPrint(List * list)
{
    Node * currentNode = list->head;
    while (currentNode != NULL) {
        printf("%d", currentNode->value);
        currentNode = currentNode->next;
        if (currentNode != NULL) {
            printf(" ");
        }
    }

    printf("\n");
}
