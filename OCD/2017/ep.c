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
    printMdc(25, 105);
    printMdc(120, 36);
}
