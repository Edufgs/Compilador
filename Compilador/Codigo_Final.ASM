.386
.model flat, stdcall
option casemap: none

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
include \masm32\include\masm32.inc
includelib \masm32\lib\masm32.lib

.data
	pulalinha db 13,10 , 0
	y dd 10 DUP(0)
	resultado dd 10 DUP(0)
	x dd 10 DUP(0)

.code
	start:
	mov y, 0
	invoke StdIn, addr x,500
	invoke StripLF, addr x
	invoke atodw, addr x
	mov x, eax
	mov eax,x
	.IF eax > y
	mov resultado, 1
	mov eax,x
	.WHILE eax > y
	mov eax, x
	mov ebx,resultado
	mul ebx
	mov resultado, eax
	mov eax, x
	sub eax, 1
	mov x, eax
	mov eax,x
	.ENDW
	.ELSE
	mov eax,x
	.IF eax < y
	mov resultado, 0
	.ELSE
	mov resultado, 1
	.ENDIF
	.ENDIF
	invoke dwtoa, resultado, addr resultado
	invoke StdOut, addr resultado
	invoke StdOut, addr pulalinha
	invoke atodw, addr resultado
	mov resultado, eax
	invoke ExitProcess, 0

	end start
