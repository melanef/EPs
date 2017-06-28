.data
quantity_message: .asciiz "Entre com a quantidade de numeros:\n"
number_message:   .asciiz "Digite os numeros, um de cada vez:\n"
result_message:	  .asciiz "O resultado do MDC dos numeros informados é: "

.text

main:
# carrega $v0 com o valor 4 para indicar ao syscall que a chamada ao SO desejada é imprimir string
	li 	$v0, 4
# carrega $a0 com o endereço do buffer da mensagem a ser exibida
	la	$a0, quantity_message
# invoca syscall: imprimir a string contida no label 'quantity_message'
	syscall
# carrega $v0 com o valor 5 para indicar ao syscall que a chamada ao SO desejada é ler inteiro da entrada padrão
	li	$v0, 5
# invoca syscall: lê um inteiro da entrada padrão	
	syscall
# copia o inteiro informado pelo usuário para o registrador $s0
	la	$s0, ($v0)
# carrega $s1 com o valor 0 -- $s1 será o registrador onde manteremos o MDC conforme for calculado a cada iteração
	li	$s1, 0
# carrega $v0 com o valor 4 para indicar ao syscall que a chamada ao SO desejada é imprimir string
	li 	$v0, 4
# carrega $a0 com o endereço do buffer da mensagem a ser exibida
	la	$a0, number_message
# invoca syscall: imprimir a string contida no label 'number_message'
	syscall
# loop: equivalente a um for
main_loop:
#   primeiro verificamos se o conteúdo de $s0 é zero, se for, pulamos para o fim do programa
	beqz 	$s0, main_end
#   carrega $v0 com o valor 5 para indicar ao syscall que a chamada ao SO desejada é ler inteiro da entrada padrão
	li	$v0, 5
#   invoca syscall: lê um inteiro da entrada padrão	
	syscall
#   copia o conteúdo de $s1 (nosso MDC atual) para o registrador $a0 (arguemtno da subrotina mdc)
	la	$a0, ($s1)
#   copia o inteiro informado pelo usuário para o registrador $a1 (argumento da subrotina mdc)
	la	$a1, ($v0)
#   armazena nossa posição atual em $ra e salta para mdc para a execução da subrotina
	jal	mdc
#   copia o conteúdo de $v0 (resultado da subrotina mdc) para $s1 substituindo nosso MDC atual pelo novo calculado
	la	$s1, ($v0)
#   decrementa $s0 para representar o passo do loop
	addi	$s0, $s0, -1
#   salta para o início do loop para nova iteração
	j	main_loop
# fim: onde chegamos depois de ler e processar todos os números informados pelo usuário
main_end:
# carrega $v0 com o valor 4 para indicar ao syscall que a chamada ao SO desejada é imprimir string
	li	$v0, 4
# carrega $a0 com o endereço do buffer da mensagem a ser exibida
	la	$a0, result_message
# invoca syscall: imprimir a string contida no label 'result_message'
	syscall
# carrega $v0 com o valor 1 para indicar ao syscall que a chamada ao  SO desejada é imprimir inteiro
	li	$v0, 1
# carrega $a0 com o valor de $s1, ou seja, o resultado calculado do MDC
	la	$a0, ($s1)
# invoca syscall: imprimir o número que representa o MDC calculado
	syscall
# carrega $v0 com o valor 10 para indicar ao syscall que a chamada ao SO desejada é fim de execução
	li	$v0, 10
# invoca syscall: fim
	syscall
