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
	lw $a0 0($fp)
	lw $a1 0($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 0($fp)
	lw $a0 0($fp)
	lw $a1 0($fp)
	nop
	subu $v0 $a0 $a1
	sw $v0 0($fp)
	lw $a0 0($fp)
	lw $a1 0($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 0($fp)
	lw $a0 0($fp)
	lw $a1 0($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 0($fp)
Main_Main_ret:
	sw $v0 24($fp)
	or $sp $0 $fp
	lw $ra 20($sp)
	lw $fp 16($sp)
	addiu $sp $sp 28
	jr $ra
	nop
	.end	Main_Main

