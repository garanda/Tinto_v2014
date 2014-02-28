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
	addiu $sp $sp -24
	sw $ra 16($sp)
	sw $fp 12($sp)
	or $fp $0 $sp
	ori $a0 $0 0
	sw $a0 0($fp)
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
	sw $v0 20($fp)
	or $sp $0 $fp
	lw $ra 16($sp)
	lw $fp 12($sp)
	addiu $sp $sp 24
	jr $ra
	nop
	.end	Main_Main

