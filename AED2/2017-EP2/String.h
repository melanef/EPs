/* String.h */
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include "List.h"

#ifndef NEF_STRING
#define NEF_STRING 1

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

#endif
