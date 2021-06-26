.globl nCk

nCk:
	bgt $a1, $a0, invalid # Test for invalid args
	bltz $a0, invalid
	bltz $a1, invalid
	
	addi $sp, $sp, -16
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)
	sw $s2, 12($sp)
	
	addi $s0, $a0, 0 # $a0
	addi $s1, $a1, 0 # $a1
	
	beqz $s1, one
	beq $s0, $s1, one
	beq $s1, 1, n
	
	addi $s0, $s0, -1 # n - 1
	addi $a0, $s0, 0
	jal nCk
	
	addi $s2, $v0, 0
	addi $a0, $s0, 0
	addi $s1, $s1, -1
	addi $a1, $s1, 0
	jal nCk
	
	add $v0, $v0, $s2
exit:	lw $ra, 0($sp)
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	lw $s2, 12($sp)
	addi $sp, $sp, 16
	jr $ra

one:	addi $v0, $0, 1
	j exit
n:	addi $v0, $a0, 0
	j exit	
invalid:
	addi $v0, $0, -1
	jr $ra
	

