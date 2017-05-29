# String.h
#include <stdio.h>

#ifndef NEF_STRING
#define NEF_STRING 1

#define EOS '\0'

typedef struct String {
    char * chars;
    int length;
} String;

String * newString(int size);

String * constructString(char * charArray, int size);

char * asCharArray(String string);

char * copyCharArray(char * source, int size);

String * explode(CharList separators, String string);

String * implode(String separator, String * strings);

String * concatStrings(String string1, String string2);

char charAt(String string, int position);

int length(char * charArray);

String * substringFromString(String source, int begin, int end);

String * substringFromCharArray(char * source, int begin, int end);


#endif
