#include <stdio.h>
#include <stdlib.h>
#include "String.h"

int main(int argc, const char ** argv)
{
    printf("Teste\n");

    int i = 0;
    char teste[] = "Testando lalalao";
    while (teste[i++] != EOS) {}

    printf("Encontrados %d chars na string '%s'\n", i, teste);

    printf("Outro teste:\n\n");
    char line[256];
    fgets(line, sizeof(line), stdin);

    String * test2 = constructString(line, 256);

    printString(test2);
}


