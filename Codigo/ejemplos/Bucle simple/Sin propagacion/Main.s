#------------------------------------------------------------------
# Copyright (c) 2010, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main_Main
#------------------------------------------------------------------

	.globl	Main_Main
	.ent	Main_Main
Main_Main:
	addiu $sp $sp -28
	sw $ra 20($sp)
	sw $fp 16($sp)
	or $fp $0 $sp
	ori $a0 $0 0
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 4($fp)
Main_Main_1:
	lw $a0 4($fp)
	ori $a1 $0 10
	slt $v0 $a0 $a1
	bne $v0 $0 Main_Main_2
	nop
	j Main_Main_3
	nop
Main_Main_2:
	lw $a0 0($fp)
	ori $a1 $0 1
	addu $v0 $a0 $a1
	sw $v0 8($fp)
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)
	lw $a0 4($fp)
	ori $a1 $0 1
	addu $v0 $a0 $a1
	sw $v0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)
	j Main_Main_1
	nop
Main_Main_3:
Main_Main_ret:
	sw $v0 24($fp)
	or $sp $0 $fp
	lw $ra 20($sp)
	lw $fp 16($sp)
	addiu $sp $sp 28
	jr $ra
	nop
	.end	Main_Main

