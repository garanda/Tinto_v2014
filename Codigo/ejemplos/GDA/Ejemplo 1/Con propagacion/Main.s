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
	addiu $sp $sp -40
	sw $ra 32($sp)
	sw $fp 28($sp)
	or $fp $0 $sp
	ori $a0 $0 0
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 4($fp)
	ori $a0 $0 0
	sw $a0 8($fp)
	ori $a0 $0 0
	sw $a0 12($fp)
	ori $a0 $0 0
	sw $a0 16($fp)
	ori $a0 $0 0
	sw $a0 20($fp)
	ori $a0 $0 0
	sw $a0 24($fp)
	ori $a0 $0 0
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 12($fp)
	ori $a0 $0 0
	sw $a0 16($fp)
	ori $a0 $0 0
	sw $a0 16($fp)
	ori $a0 $0 0
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 24($fp)
Main_Main_ret:
	sw $v0 36($fp)
	or $sp $0 $fp
	lw $ra 32($sp)
	lw $fp 28($sp)
	addiu $sp $sp 40
	jr $ra
	nop
	.end	Main_Main

