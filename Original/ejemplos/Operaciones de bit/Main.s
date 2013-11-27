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
	addiu $sp $sp -72
	sw $ra 64($sp)
	sw $fp 60($sp)
	or $fp $0 $sp
	ori $a0 $0 4
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	subu $v0 $0 $a0
	sw $v0 16($fp)
	lw $a0 16($fp)
	nop
	sw $a0 0($fp)
	ori $a0 $0 3
	sw $a0 20($fp)
	lw $a0 20($fp)
	nop
	sw $a0 4($fp)
	lw $a0 4($fp)
	nop
	nor $v0 $0 $a0
	sw $v0 32($fp)
	lw $a0 32($fp)
	lw $a1 0($fp)
	nop
	xor $v0 $a0 $a1
	sw $v0 28($fp)
	lw $a0 4($fp)
	lw $a1 0($fp)
	nop
	sll $v0 $a0 $a1
	sw $v0 48($fp)
	lw $a0 48($fp)
	lw $a1 4($fp)
	nop
	srl $v0 $a0 $a1
	sw $v0 44($fp)
	ori $a0 $0 1
	sw $a0 52($fp)
	lw $a0 44($fp)
	lw $a1 52($fp)
	nop
	sra $v0 $a0 $a1
	sw $v0 40($fp)
	lw $a0 4($fp)
	lw $a1 40($fp)
	nop
	and $v0 $a0 $a1
	sw $v0 36($fp)
	lw $a0 28($fp)
	lw $a1 36($fp)
	nop
	or $v0 $a0 $a1
	sw $v0 24($fp)
	lw $a0 24($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp -4
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 56($fp)
	addiu $sp $sp 4
Main_Main_ret:
	sw $v0 68($fp)
	or $sp $0 $fp
	lw $ra 64($sp)
	lw $fp 60($sp)
	addiu $sp $sp 72
	jr $ra
	nop
	.end	Main_Main

