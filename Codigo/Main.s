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
	addiu $sp $sp -64
	sw $ra 56($sp)
	sw $fp 52($sp)
	or $fp $0 $sp
	ori $a0 $0 2
	sw $a0 4($fp)
	ori $a0 $0 3
	sw $a0 8($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 28($fp)
	lw $a0 28($fp)
	nop
	sw $a0 0($fp)
	lw $a0 8($fp)
	ori $a1 $0 2
	addu $v0 $a0 $a1
	sw $v0 32($fp)
	lw $a0 32($fp)
	nop
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 16($fp)
	lw $a0 16($fp)
	lw $a1 12($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 36($fp)
	lw $a0 36($fp)
	nop
	sw $a0 0($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 40($fp)
	lw $a0 40($fp)
	nop
	sw $a0 16($fp)
	lw $a0 4($fp)
	lw $a1 8($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 44($fp)
	lw $a0 44($fp)
	nop
	sw $a0 24($fp)
	addiu $sp $sp -4
	ori $a0 $0 3
	sw $a0 0($sp)
	jal Main_doble_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 48($fp)
	addiu $sp $sp 4
	lw $a0 48($fp)
	nop
	sw $a0 24($fp)
Main_Main_ret:
	sw $v0 60($fp)
	or $sp $0 $fp
	lw $ra 56($sp)
	lw $fp 52($sp)
	addiu $sp $sp 64
	jr $ra
	nop
	.end	Main_Main

#------------------------------------------------------------------
# Main_doble_0
#------------------------------------------------------------------

	.globl	Main_doble_0
	.ent	Main_doble_0
Main_doble_0:
	addiu $sp $sp -16
	sw $ra 8($sp)
	sw $fp 4($sp)
	or $fp $0 $sp
	ori $a0 $0 2
	lw $a1 16($fp)
	nop
	mult $a0 $a1
	mflo $v0
	sw $v0 0($fp)
	lw $v0 0($fp)
	j Main_doble_0_ret
	nop
Main_doble_0_ret:
	sw $v0 12($fp)
	or $sp $0 $fp
	lw $ra 8($sp)
	lw $fp 4($sp)
	addiu $sp $sp 16
	jr $ra
	nop
	.end	Main_doble_0

