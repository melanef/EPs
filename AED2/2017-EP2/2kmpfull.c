/* KMP */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

/****** LIST.h *****/
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
/***** /LIST.h *****/

/****** STRING.h *****/
#define EOS '\0'
#define EOL '\n'

typedef struct String {
    char * chars;
    int length;
} String;

String * newString(int size);

String * constructString(char * charArray, int size);

char * asCharArray(String * source);

char * copyCharArray(char * source, int size);

String ** explode(List * separators, String * string);

void implode(String * separator, String ** strings);

String * concatStrings(String * string1, String * string2);

char charAt(String * string, int position);

int stringLength(char * charArray);

String * substringFromString(String * source, int begin, int end);

String * substringFromCharArray(char * source, int begin, int end);

void printString(String * string);
/***** /STRING.h *****/

/****** KMP.h *****/
int * KMPFailureFunction(String * word);

List * KMPMatch(String * text, String * word);
/***** /KMP.h *****/

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

/****** LIST.c *****/
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
/***** /LIST.c *****/

/****** STRING.c *****/
String * newString(int size)
{
    String * string = (String *) malloc(sizeof(String));
    string->chars = (char *) malloc((size + 1) * sizeof(char));
    string->length = size;

    string->chars[size] = EOS;

    return string;
}

String * constructString(char * charArray, int size)
{
    int i, c = 0;

    for (i = 0; i < size; i++) {
        if (charArray[i] == EOS || charArray[i] == EOL) {
            break;
        }

        c++;
    }

    String * string = newString(c);

    for (i = 0; i < c; i++) {
        string->chars[i] = charArray[i];
    }


    return string;
}

char * asCharArray(String * source)
{
    char * char_array = copyCharArray(source->chars, source->length);

    return char_array;
}

char * copyCharArray(char * source, int size)
{
    int i;

    char * result = (char *) malloc((size + 1) * sizeof(char));

    for (i = 0; i < size; i++) {
        result[i] = source[i];
    }

    result[i] = EOS;

    return result;
}

String ** explode(List * separators, String * string)
{
    bool isSeparator;
    int begin = 0, quantity = 0, i, j;
    Node * separator;
    String ** strings;

    // Fase de contagem
    for (i = 0; i < string->length; i++) {
        char c = string->chars[i];

        isSeparator = false;
        separator = separators->head;
        while (separator != NULL) {
            if (c == (char) separator->value) {
                isSeparator = true;
                break;
            }

            separator = separator->next;
        }

        if (isSeparator) {
            if (i > begin) {
                quantity++;
                begin = i + 1;
            }
        }
    }

    // Fase de alocação
    strings = (String **) malloc(quantity * sizeof(String *));

    // Fase de criação
    for (i = 0, j = 0; i < string->length; i++) {
        char c = string->chars[i];

        isSeparator = false;
        separator = separators->head;
        while (separator != NULL) {
            if (c == separator->value) {
                isSeparator = true;
                break;
            }

            separator = separator->next;
        }

        if (isSeparator) {
            if (i > begin) {
                strings[j] = substringFromString(string, begin, i);
                begin = i + 1;
            }
        }
    }

    return strings;
}

void implode(String * separator, String ** strings)
{}

String * concatStrings(String * string1, String * string2)
{
    int i, j;
    String * string = newString(string1->length + string2->length);

    for (i = 0, j = 0; i < string1->length; i++, j++) {
        string->chars[j] = string1->chars[i];
    }

    for (i = 0; i < string1->length; i++, j++) {
        string->chars[j] = string2->chars[i];
    }

    return string;
}

char charAt(String * string, int position)
{
    if (!(string->length > position)) {
        return EOS;
    }

    return string->chars[position];
}

int stringLength(char * charArray)
{
    int c = 0;
    while (charArray[c++] != EOS) {}
    return c;
}

String * substringFromString(String * source, int begin, int end)
{
    int i, j;
    int size = (end - begin) + 1;

    if (!(size > 0)) {
        return NULL;
    }

    if (!(end <= source->length)) {
        return NULL;
    }

    String * string = newString(size);

    for (i = 0, j = begin; j < end; i++, j++) {
        string->chars[i] = source->chars[j];
    }

    return string;
}

String * substringFromCharArray(char * source, int begin, int end)
{
    int i, j;
    int size = (end - begin) + 1;

    if (!(size > 0)) {
        return NULL;
    }

    if (!(end < stringLength(source))) {
        return NULL;
    }

    String * string = newString(size);

    for (i = 0, j = begin; j < end; i++, j++) {
        string->chars[i] = source[j];
    }

    return string;
}

void printString(String * string)
{
    printf("%s\n", string->chars);
}
/***** /STRING.c *****/

/****** KMP.c *****/
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
                if ((i == (text->length - 1) || charAt(text, i + 1) == ' ') && (i - j == 0 || charAt(text, i - j - 1) == ' ')) {
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
/***** /KMP.c *****/
