.globl makes10 intMax close10 dateFashion
.text

makes10_true:
	addi $v0, $0, 1
	jr $ra

makes10:
	addi $v0, $0, 0
	add $t1, $a0, $a1
	beq $a0, 10, makes10_true
	beq $a1, 10, makes10_true
	beq $t1, 10, makes10_true
	addi $v0, $v0, 0
	jr $ra
	
a:	addi $v0, $a0, 0
	jr $ra
b:	addi $v0, $a1, 0
	jr $ra
c:	addi $v0, $a2, 0
	jr $ra

intMax:
	sge $t0, $a0, $a1
	sge $t1, $a0, $a2
	and $t0, $t0, $t1
	
	sge $t2, $a1, $a0
	sge $t3, $a1, $a2
	and $t2, $t2, $t3
	
	sge $t4, $a2, $a0
	sge $t5, $a2, $a1
	and $t4, $t4, $t5
	
	beq $t0, 1, a
	beq $t2, 1, b
	beq $t4, 1, c
	jr $ra

tie:	addi $v0, $0, 0
	jr $ra

a_close10:
	addi $v0, $a0, 0
	jr $ra

b_close10: 
	addi $v0, $a1, 0
	jr $ra

close10:
	subi $t0, $a0, 10
	subi $t1, $a1, 10
	
	abs $t0, $t0
	abs $t1, $t1
	
	beq $t0, $t1, tie
	blt $t0, $t1, a_close10
	blt $t1, $t0, b_close10
	jr $ra

yes:	addi $v0, $0, 2
	j finish

no:	addi $v0, $0, 0
	j finish

dateFashion:
	sge $t0, $a0, 8
	sge $t1, $a0, 3
	and $t0, $t0, $t1
	
	sge $t2, $a0, 3
	sge $t3, $a1, 8
	and $t2, $t2, $t3
	
	beq $t0, 1, yes
	beq $t2, 1, yes
	
	slti $t4, $a0, 3
	slti $t5, $a1, 3
	
	beq $t4, 1, no
	beq $t5, 1, no
	
	addi $v0, $0, 1
finish: addi $v0, $v0, 0
	jr $ra
	
	
	
