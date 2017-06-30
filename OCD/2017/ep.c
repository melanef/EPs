/*
  Organização de Computadores Digitais
  Professora Doutora Gisele da Silva Craveiro

  Alunos:
  Amauri de Melo Junior - 8516650
  Brenno Cremoini - 9778860
*/

#include <stdlib.h>
#include <stdio.h>

int mdc(int x, int y)
{
    if (y == 0) {
        return x;
    }

    if (x == y) {
        return x;
    }

    if (x < y) {
        return mdc(y,x);
    }

    int r = x % y;
    if (r == 0) {
        return y;
    }

    return mdc(y, r);
}

void printMdc(int x, int y)
{
    printf("MDC( %d , %d ) = %d\n", x, y, mdc(x, y));
}

int main(const int argc, const char argv[])
{
    int n, i, current_mdc = 0;
    int * numbers;

    printf("Entre com a quantidade de números:\n");
    scanf("%u", &n);

    numbers = (int *) malloc(n * sizeof(int));

    printf("Digite os números, um de cada vez:\n");
    for (i = 0; i < n; i++) {
        scanf("%u", &numbers[i]);
        current_mdc = mdc(numbers[i], current_mdc);
    }

    printf("O MDC é %d\n", current_mdc);
}
