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
	addiu $sp $sp -48
	sw $ra 40($sp)
	sw $fp 36($sp)
	or $fp $0 $sp
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 20($fp)
	lw $a0 20($fp)
	nop
	sw $a0 0($fp)
	lw $a0 4($fp)
	lw $a1 12($fp)
	nop
	subu $v0 $a0 $a1
	sw $v0 24($fp)
	lw $a0 24($fp)
	nop
	sw $a0 4($fp)
	lw $a0 8($fp)
	lw $a1 12($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 28($fp)
	lw $a0 28($fp)
	nop
	sw $a0 8($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 32($fp)
	lw $a0 32($fp)
	nop
	sw $a0 16($fp)
Main_Main_ret:
	sw $v0 44($fp)
	or $sp $0 $fp
	lw $ra 40($sp)
	lw $fp 36($sp)
	addiu $sp $sp 48
	jr $ra
	nop
	.end	Main_Main

