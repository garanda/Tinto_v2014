#------------------------------------------------------------------
# Copyright (c) 2010, Francisco Jos� Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main_Main
#------------------------------------------------------------------

	.globl	Main_Main
	.ent	Main_Main
Main_Main:
	addiu $sp $sp -20
	sw $ra 12($sp)
	sw $fp 8($sp)
	or $fp $0 $sp
	addiu $sp $sp -4
	ori $a0 $0 5
	sw $a0 0($fp)
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Main_factorial_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 0($fp)
	addiu $sp $sp 4
Main_Main_ret:
	sw $v0 16($fp)
	or $sp $0 $fp
	lw $ra 12($sp)
	lw $fp 8($sp)
	addiu $sp $sp 20
	jr $ra
	nop
	.end	Main_Main

#------------------------------------------------------------------
# Main_factorial_0
#------------------------------------------------------------------

	.globl	Main_factorial_0
	.ent	Main_factorial_0
Main_factorial_0:
	addiu $sp $sp -24
	sw $ra 16($sp)
	sw $fp 12($sp)
	or $fp $0 $sp
	lw $a0 0($fp)
	ori $a1 $0 1
	slt $v0 $a1 $a0
	beq $v0 $0 Main_factorial_0_1
	nop
	j Main_factorial_0_2
	nop
Main_factorial_0_1:
	ori $v0 $0 1
	j Main_factorial_0_ret
	nop
Main_factorial_0_2:
	addiu $sp $sp -4
	lw $a0 0($fp)
	ori $a1 $0 1
	subu $v0 $a0 $a1
	sw $v0 0($fp)
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Main_factorial_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 0($fp)
	addiu $sp $sp 4
	lw $a0 0($fp)
	lw $a1 0($fp)
	nop
	mult $a0 $a1
	mflo $v0
	sw $v0 0($fp)
	lw $v0 0($fp)
	j Main_factorial_0_ret
	nop
Main_factorial_0_ret:
	sw $v0 20($fp)
	or $sp $0 $fp
	lw $ra 16($sp)
	lw $fp 12($sp)
	addiu $sp $sp 24
	jr $ra
	nop
	.end	Main_factorial_0

