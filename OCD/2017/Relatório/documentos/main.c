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

