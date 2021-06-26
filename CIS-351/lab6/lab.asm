.globl monkeyTrouble sleepIn posNeg
.text

monkeyTrouble:
	xor $v0, $a0, $a1
	not $v0, $v0
	andi $v0, $v0, 1
	jr $ra

sleepIn:
	not $v0, $a0
	andi $v0, $v0, 1
	or $v0, $v0, $a1
	jr $ra

posNeg:
	slti $t0, $a0, 0
	slti $t1, $a1, 0
	
	xor $v0, $t0, $t1
	not $t2, $a2
	andi $t2, $t2, 1
	and $v0, $v0, $t2
	
	and $t3, $t0, $t1
	and $t3, $t3, $a2
	
	or $v0, $v0, $t3
	jr $ra
