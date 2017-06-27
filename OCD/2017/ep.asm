.data
quantity_message: .asciiz "Entre com a quantidade de numeros:\n"
number_message:   .asciiz "Digite os numeros, um de cada vez:\n"
result_message:	  .asciiz "O resultado do MDC dos numeros informados é: "

.text

main:
	li 	$v0, 4
	la	$a0, quantity_message
	syscall
	li	$v0, 5
	syscall
	la	$s0, ($v0)
	li	$s1, 0
	
	li 	$v0, 4
	la	$a0, number_message
	syscall
main_loop:
	beqz 	$s0, main_end

	li	$v0, 5
	syscall
	
	la	$a0, ($s1)
	la	$a1, ($v0)
	jal	mdc
	la	$s1, ($v0)
	addi	$s0, $s0, -1
	j	main_loop
	
main_end:
	li	$v0, 4
	la	$a0, result_message
	syscall
	li	$v0, 1
	la	$a0, ($s1)
	syscall
	li	$v0, 10
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
