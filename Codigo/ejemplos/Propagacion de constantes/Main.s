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
	addiu $sp $sp -28
	sw $ra 20($sp)
	sw $fp 16($sp)
	or $fp $0 $sp
	ori $a0 $0 1
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 4($fp)
	ori $a0 $0 0
	sw $a0 8($fp)
	ori $a0 $0 1
	sw $a0 12($fp)
Main_Main_ret:
	sw $v0 24($fp)
	or $sp $0 $fp
	lw $ra 20($sp)
	lw $fp 16($sp)
	addiu $sp $sp 28
	jr $ra
	nop
	.end	Main_Main
