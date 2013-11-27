#------------------------------------------------------------------
# Copyright (c) 2007, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------

#------------------------------------------------------------------
# Console_print_1
#------------------------------------------------------------------

	.globl	Console_print_1
	.ent	Console_print_1
Console_print_1:
	addiu $sp $sp -12
	sw $ra 4($sp)
	sw $fp 0($sp)
	or $fp $0 $sp
	lw $a0 12($sp)
	ori $v0 $0 11
	syscall
Console_print_1_ret:
	sw $v0 8($fp)
	or $sp $0 $fp
	lw $ra 4($sp)
	lw $fp 0($sp)
	addiu $sp $sp 12
	jr $ra
	nop
	.end	Console_print_1

#------------------------------------------------------------------
# Console_print_0
#------------------------------------------------------------------

	.globl	Console_print_0
	.ent	Console_print_0
Console_print_0:
	addiu $sp $sp -12
	sw $ra 4($sp)
	sw $fp 0($sp)
	or $fp $0 $sp
	lw $a0 12($sp)
	ori $v0 $0 1
	syscall
Console_print_0_ret:
	sw $v0 8($fp)
	or $sp $0 $fp
	lw $ra 4($sp)
	lw $fp 0($sp)
	addiu $sp $sp 12
	jr $ra
	nop
	.end	Console_print_0


