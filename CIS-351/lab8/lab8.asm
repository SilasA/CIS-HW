.globl indexOf max sum13 sum67
.text

indexOf:
	addi $t0, $0, 0
notequal: 
	lw $t1, 0($a1)
	beq $t1, $a0, equal
	bltz $t1, endzero
	addi $a1, $a1, 4
	addi $t0, $t0, 1
	j notequal
endzero: addi $v0, $0, -1
	j end
equal: 	add $v0, $0, $t0
end:	addi $v0, $v0, 0
	jr $ra

max: # $a0 = array $a1 = length
	addi $t0, $a0, 0
	addi $t3, $0, 1
	sll $t2, $t3, 31 # max set to minimum
	addi $t3, $0, 0 #index
loop:	bge $t3, $a1, endmax
   	lw $t1, 0($t0) # array value
	bge $t1, $t2, setmax
	j continue
setmax: addi $t2, $t1, 0
continue:
	addi $t0, $t0, 4
	addi $t3, $t3, 1
	j loop
endmax: add $v0, $0, $t2
	jr $ra

sum13:  # $a0 = array $a1 = size
	addi $t0, $a0, 0 #array addr
	addi $t2, $0, 0  #sum
	addi $t3, $0, 0  #index
sum13loop:
	bge $t3, $a1, endsum13
	lw $t1, 0($t0)
	beq $t1, 13, skip
	add $t2, $t2, $t1
	addi $t0, $t0, 4
	addi $t3, $t3, 1
	j sum13loop
skip:	addi $t0, $t0, 8
	addi $t3, $t3, 2
	j sum13loop
endsum13:
	add $v0, $t2, 0
	jr $ra

sum67: 	# $a0 = array $a1 = size
	addi $t0, $a0, 0
	addi $t2, $0, 0 # sum
	addi $t3, $0, 0 # index
	addi $t4, $0, 0 # skip flag
sum67loop:
	bge $t3, $a1, endsum67
	lw $t1, 0($t0)
	beq $t1, 6, startskip
	blez $t4 summate
	beq $t1, 7, endskip
return: addi $t0, $t0, 4
	addi $t3, $t3, 1
	j sum67loop
summate:
	add $t2, $t2, $t1
	j return
startskip:
	addi $t4, $0, 1
	j return
endskip:
	addi $t4, $0, 0
	j return
endsum67:
	add $v0, $t2, 0
	jr $ra

