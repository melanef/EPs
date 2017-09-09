/* TRIE */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

/****** LIST.H *****/
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
/***** /LIST.H *****/

/****** STRING.H *****/
#define EOS '\0'

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
/***** /STRING.H *****/

/****** TRIE.H *****/
typedef struct trie {
    List * sub;
} Trie;

typedef struct trieNode {
    char c;
    List * sub;
    List * positions;
} TrieNode;


Trie * trieNew();

Trie * trieBuildFromText(String * text);

TrieNode * trieNodeNew(char c);

void trieInsert(Trie * trie, String * word, int position);

TrieNode * trieFindNode(List * sub, char x);

TrieNode * trieFindOrCreateNode(List * sub, char x);

List * trieSearch(Trie * trie, String * word);

void trieNodePrint(TrieNode * node);
/***** /TRIE.H *****/

int main(int argc, const char ** argv)
{
    int wordQuantity = 0, i;
    char textInput[10001];
    char wordInput[51];

    //textInput = fgets(textInput, sizeof(textInput), stdin);

    String * text = constructString(fgets(textInput, sizeof(textInput), stdin), sizeof(textInput));

    int read = scanf("%d\n", &wordQuantity);

    if (!read) {
        return 1;
    }

    Trie * searchTrie = trieBuildFromText(text);

    List * wordList = listNew();
    for (i = 0; i < wordQuantity; i++) {
        if (scanf("%s", wordInput)) {
            String * word = constructString(wordInput, sizeof(wordInput));
            listInsert(wordList, (uintptr_t) word);
        }
    }

    Node * currentNode = wordList->head;
    while (currentNode != NULL) {
        String * currentWord = (String *) (uintptr_t) currentNode->value;
        List * found = trieSearch(searchTrie, currentWord);
        if (found == NULL) {
            printf("-1\n");
        }

        if (found != NULL) {
            listPrint(found);
        }

        currentNode = currentNode->next;
    }
    /*
    */

    return 0;
}


/****** LIST.C *****/
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
/***** /LIST.C *****/

/****** STRING.C *****/
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
    int i, c;

    for (i = 0; i < size; i++) {
        if (charArray[i] == EOS) {
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

    if (!(end < source->length)) {
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
/***** /STRING.C *****/

/****** TRIE.C *****/
Trie * trieNew()
{
    Trie * trie = (Trie *) malloc(sizeof(Trie));
    trie->sub = listNew();

    return trie;
}

Trie * trieBuildFromText(String * text)
{
    Trie * new = trieNew();

    int i, start = 0, end;
    bool found = false;
    for (i = 0; i < text->length; i++) {
        if (charAt(text, i) == ' ') {
            end = i;

            if (end > start) {
                //printString(substringFromString(text, start, end));
                trieInsert(new, substringFromString(text, start, end), start);
            }

            start = i + 1;
        }
    }


    return new;
}

TrieNode * trieNodeNew(char c)
{
    TrieNode * node = (TrieNode *) malloc(sizeof(TrieNode));
    node->sub = listNew();
    node->positions = listNew();
    node->c = c;

    return node;
}

void trieInsert(Trie * trie, String * word, int position)
{
    List * sub = trie->sub;
    TrieNode * node = NULL;
    int i;

    for (i = 0; i < word->length - 1; i++) {
        node = trieFindOrCreateNode(sub, charAt(word, i));
        sub = node->sub;
    }

    listInsert(node->positions, position);
}

TrieNode * trieFindNode(List * sub, char x)
{
    Node * currentNode = sub->head;
    while (currentNode != NULL) {
        TrieNode * currentTrieNode = (TrieNode *) (uintptr_t) currentNode->value;
        if (currentTrieNode->c == x) {
            return currentTrieNode;
        }

        currentNode = currentNode->next;
    }

    return NULL;
}

TrieNode * trieFindOrCreateNode(List * sub, char x)
{
    TrieNode * found = trieFindNode(sub, x);
    if (found != NULL) {
        return found;
    }

    TrieNode * new = trieNodeNew(x);

    listInsert(sub, (uintptr_t) new);

    return new;
}

List * trieSearch(Trie * trie, String * word)
{
    List * sub = trie->sub;
    TrieNode * node = NULL;
    int i;

    for (i = 0; i < word->length; i++) {
        node = trieFindNode(sub, charAt(word, i));

        if (node == NULL) {
            return NULL;
        }

        sub = node->sub;
    }

    return node->positions;
}

void trieNodePrint(TrieNode * node)
{
    printf("Char: %c\nPositions: ", node->c);
    listPrint(node->positions);
}
/***** /TRIE.C *****/
