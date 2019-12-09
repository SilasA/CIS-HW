.globl wackySum combineFour
	
j main
	
wackySum: # $a0: a, $a1: b, $a2: c

	# save registers
	addi $sp, $sp, -16
	sw $s0, 0($sp)
	sw $s1, 4($sp)
	sw $s2, 8($sp)
	sw $ra, 12($sp)
	
	add $s0, $0, $a0
	add $s1, $0, $a1
	add $s2, $0, $a2
	
	addi $t0, $0, 0 # sum
	add $t1, $0, $s0 # i
loop:	bgt $t1, $s1, exitSum
	
	addi $sp, $sp, -8
	sw $t0, 0($sp)
	sw $t1, 4($sp)
	
	# $a0
	addi $a0, $t1, 0
	# $a1
	addi $a1, $a0, 1
	sra $a1, $a1, 1
	# $a2
	addi $a2, $a0, 2
	sra $a2, $a2, 1
	# $a3
	addi $a3, $a0, 3
	
	jal combineFour
	
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	addi $sp, $sp, 8
	
	add $t0, $t0, $v0
	
	add $t1, $t1, $s2
	j loop

exitSum:
	addi $v0, $t0, 0
	
	# restore registers
	lw $s0, 0($sp)
	lw $s1, 4($sp)
	lw $s2, 8($sp)
	lw $ra, 12($sp)
	addi $sp, $sp, 16
	
	jr $ra
	
combineFour:
	add $t0, $a0, $a1
	add $t0, $t0, $a2
	add $t0, $t0, $a3
	
	andi $t1, $t0, 1
	beq $t1, 1, div2
	add $v0, $0, $t0
	j exitc4 
div2:	sra $v0, $t0, 1
exitc4:	jr $ra

main:	addi $a0, $0, 21
	addi $a1, $0, 26
	addi $a2, $0, 1
	
	jal wackySum
	
	addi $a0, $0, 33
	addi $a1, $0, 42
	addi $a2, $0, 2
	
	jal wackySum