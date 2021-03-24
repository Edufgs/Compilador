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
	inputHandle dd 0
	outputHandle dd 0
	digita db 'Digite: '
	tamanho dd 0
	console_count dd 0
	cAio dd 0
	xaxa90 dd 0
	xuxa dd 0
	y dd 1
	x dd 0

.code
	start:
	invoke GetStdHandle, STD_INPUT_HANDLE
	mov inputHandle, eax
	invoke ReadConsole, inputHandle, addr xaxa90, sizeof xaxa90, addr console_count, NULL

	mov esi, offset xaxa90

	proximo:
	mov al, [esi]
	inc esi
	cmp al, 48
	jl terminar
	cmp al, 58
	jl proximo

	terminar:
	dec esi
	xor al, al
	mov [esi], al

	invoke atodw, addr xaxa90
	mov xaxa90, eax
	invoke GetStdHandle, STD_OUTPUT_HANDLE
	mov outputHandle, eax
	invoke dwtoa, x, addr x
	invoke StrLen, addr x
	mov tamanho, eax	
	invoke WriteConsole, outputHandle, addr x, sizeof tamanho, addr console_count, NULL

	invoke ExitProcess, 0

	end start
