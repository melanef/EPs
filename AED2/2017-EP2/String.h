# String.h
#include <stdio.h>

#ifndef NEF_STRING
#define NEF_STRING 1

#define EOS '\0'

typedef struct String {
    char * char_string;
    int length;
} String;

String * construct(char * char_string, int size);

String * explode(String delimiters, String string);

String * implode(String separator, String string1, String string2);

char charAt(String string, int position);

char * asCharArray(String string);

int length(char * char_string);

String * substring(String string, int begin, int end);

#endif
