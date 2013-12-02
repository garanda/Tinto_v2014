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
	ori $a0 $0 7
	sw $a0 0($fp)
	ori $a0 $0 7
	lw $a1 4($fp)
	nop
	subu $v0 $a0 $a1
	sw $v0 24($fp)
	lw $a0 24($fp)
	nop
	sw $a0 8($fp)
	ori $a0 $0 1
	sw $a0 12($fp)
	ori $a0 $0 0
	sw $a0 16($fp)
	lw $a0 12($fp)
	nop
	bne $a0 $0 Main_Main_3
	nop
	j Main_Main_2
	nop
Main_Main_3:
	lw $a0 16($fp)
	nop
	bne $a0 $0 Main_Main_1
	nop
	j Main_Main_2
	nop
Main_Main_1:
	ori $a0 $0 7
	sw $a0 4($fp)
Main_Main_2:
	ori $a0 $0 0
	sw $a0 20($fp)
Main_Main_ret:
	sw $v0 36($fp)
	or $sp $0 $fp
	lw $ra 32($sp)
	lw $fp 28($sp)
	addiu $sp $sp 40
	jr $ra
	nop
	.end	Main_Main

