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
	addiu $sp $sp -60
	sw $ra 52($sp)
	sw $fp 48($sp)
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
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 28($fp)
	lw $a0 28($fp)
	nop
	sw $a0 0($fp)
	lw $a0 8($fp)
	lw $a1 20($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 32($fp)
	lw $a0 32($fp)
	nop
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 16($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 36($fp)
	lw $a0 36($fp)
	nop
	sw $a0 16($fp)
	lw $a0 16($fp)
	lw $a1 12($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 40($fp)
	lw $a0 40($fp)
	nop
	sw $a0 0($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 44($fp)
	lw $a0 44($fp)
	nop
	sw $a0 24($fp)
Main_Main_ret:
	sw $v0 56($fp)
	or $sp $0 $fp
	lw $ra 52($sp)
	lw $fp 48($sp)
	addiu $sp $sp 60
	jr $ra
	nop
	.end	Main_Main

