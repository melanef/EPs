/* String.c */
#include "String.h"

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
