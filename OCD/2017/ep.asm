#  Organização de Computadores Digitais
#  Professora Doutora Gisele da Silva Craveiro
#
#  Alunos:
#  Amauri de Melo Junior - 8516650
#  Brenno Cremoini - 9778860


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

mdc:
# primeiro de tudo, salvamos o valor de ra na pilha (para saber de onde viemos)
	addiu	$sp, $sp, -4
	sw	$ra, 0($sp)
# agora podemos começar
#   o segundo parametro é zero? se sim, pula para o trecho que retorna o primeiro parametro
	beqz	$a1, mdc_retorna_a0
#   os parametros são iguais? se sim, pula para o trecho que retorna o primeiro parametro
	beq	$a0, $a1, mdc_retorna_a0
#   o primeiro parametro é menor do que o segundo? se sim, pula para o trecho que os inverte e chama mdc recursivamente
	blt	$a0, $a1, mdc_retorna_mdc_invertido
#   divide o primeiro parametro pelo segundo
	div	$a0, $a1
#   copia o resto da divisão para a variável temporária t0
	mfhi	$t0
#   o valor de t0 é zero? se sim, significa que são múltiplos entre si, portanto pula para o trecho que retorna o segundo parametro
	beqz	$t0, mdc_retorna_a1
#   se não pulamos até aqui, o que resta é chamar mdc recursivamente, para isso, colocamos os novos parametros nas posicoes a0 e a1 e pulamos para o começo de mdc
	la	$a0, ($a1)
	la	$a1, ($t0)
	jal	mdc
# quando voltarmos, precisamos desempilhar o endereço e voltar, por isso, copiamos o que está no topo da pilha nesse instante para ra, movemos o ponteiro da pilha e pulamos para o ponto de ra
	lw	$ra, 0($sp)
	addiu	$sp, $sp, 4
	jr	$ra

mdc_retorna_mdc_invertido:
# copia o valor do primeiro parametro para a variável temporária t0
	la	$t0, ($a0)
# copia o valor do segundo parametro para a variável de parameto a0
	la	$a0, ($a1)
# copia o valor da variável temporária t0 para a variável de parametro a1
	la	$a1, ($t0)
# pulamos para o começo de mdc
	jal	mdc
# quando voltarmos, precisamos desempilhar o endereço e voltar, por isso, copiamos o que está no topo da pilha nesse instante para ra, movemos o ponteiro da pilha e pulamos para o ponto de ra
	lw	$ra, 0($sp)
	addiu	$sp, $sp, 4
	jr 	$ra
mdc_retorna_a0:
# armazenamos em v0 o valor do primeiro parametro, armazenado até então na variável de parametro a0
	la	$v0, ($a0)
# desempilhamos o endereço para voltar e pulamos
	lw	$ra, 0($sp)
	addiu	$sp, $sp, 4
	jr	$ra
mdc_retorna_a1:
# armazenamos em v0 o valor do primeiro parametro, armazenado até então na variável de parametro a0
	la	$v0, ($a1)
# desempilhamos o endereço para voltar e pulamos
	lw	$ra, 0($sp)
	addiu	$sp, $sp, 4
	jr	$ra
