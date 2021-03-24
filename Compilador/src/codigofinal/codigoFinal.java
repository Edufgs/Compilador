package codigofinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Esse é o gerador de codigo final. 
 * 
 * @author Eduardo Gonçalves da Silva
 */
public class codigoFinal {
    
    private static BufferedReader lerCodigo;
    private static FileWriter codigoFinal;
            
    public static void codigoFinal(String comandoVetor[]){
        try{       
            FileInputStream abreCodigo = new FileInputStream("Codigo Intermediario.txt"); //abre o arquivo
            InputStreamReader codigo= new InputStreamReader(abreCodigo);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            lerCodigo = new BufferedReader(codigo);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
            
            codigoFinal = new FileWriter(new File("Codigo Final.ASM"));           
            
            System.out.println("===============================================================================");
            System.out.println("\nInicia Gerador de Codigo Final:\n");
            
            iniciaArquivoFinal();//Inicia com as bibliotecas e ouros 
            
            String linha = lerCodigo.readLine();//pega a proxima linha
            String linhaVetor[];
            
            while(linha!=null){
                linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor                
                gerarCodigo(linhaVetor);
                linha = lerCodigo.readLine();//pega a proxima linha
            }
            codigoFinal.write("\n\tend start\n");
            
            abreCodigo.close();
            codigoFinal.close();
            System.out.println("Codigo Final: Pronto!!!");
            System.out.println("===============================================================================");
        }catch (FileNotFoundException ex) {
             System.out.println("Erro em abrir o arquivo: " + ex);
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }            
    }
    
    private static void iniciaArquivoFinal() throws IOException{
        codigoFinal.write(".386\n.model flat, stdcall\noption casemap: none\n");
        codigoFinal.write("\ninclude \\masm32\\include\\windows.inc\ninclude \\masm32\\include\\user32.inc\ninclude \\masm32\\include\\kernel32.inc\nincludelib \\masm32\\lib\\user32.lib\nincludelib \\masm32\\lib\\kernel32.lib\n");
        codigoFinal.write("include \\masm32\\include\\masm32.inc\nincludelib \\masm32\\lib\\masm32.lib\n");
        codigoFinal.write("\n.data\n");
        codigoFinal.write("\tinputHandle dd 0\n\toutputHandle dd 0\n");
        codigoFinal.write("\tdigita db 'Digite: '\n");
        codigoFinal.write("\ttamanho dd 0\n\tconsole_count dd 0\n");
    }
    
    private static void gerarCodigo(String linhaVetor[]) throws IOException{
        switch(linhaVetor[0]){
            case "_var":                
                do{
                    codigoFinal.write("\t"+linhaVetor[1]+" dd ");//aloca bytes de armazenamento
                    if(linhaVetor.length == 4){//se estiver criando a variavel inserindo um numero
                        codigoFinal.write(linhaVetor[3]+"\n");//adiciona o numero na variavel
                    }else{
                        codigoFinal.write("0\n");//adiciona 0 na variavel
                    }
                     String linha = lerCodigo.readLine();//pega a proxima linha
                     linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
                                          
                }while(linhaVetor[0].equals("_var"));
                codigoFinal.write("\n.code\n");//inicia a parte do codigo
                codigoFinal.write("\tstart:\n");//começa o programa
                gerarCodigo(linhaVetor);
                break;
            case "ler":
                codigoFinal.write("\tinvoke GetStdHandle, STD_INPUT_HANDLE\n\tmov inputHandle, eax\n");//inicia a entrada
                codigoFinal.write("\tinvoke ReadConsole, inputHandle, addr "+ linhaVetor[1] +", sizeof " + linhaVetor[1] + ", addr console_count, NULL\n\n");//le do console
                codigoFinal.write("\tmov esi, offset "+ linhaVetor[1] +"\n\n\tproximo:\n\tmov al, [esi]\n\tinc esi\n\tcmp al, 48\n\tjl terminar\n\tcmp al, 58\n\tjl proximo\n\n\tterminar:\n\tdec esi\n\txor al, al\n\tmov [esi], al\n\n\tinvoke atodw, addr "+ linhaVetor[1] +"\n\tmov "+ linhaVetor[1] +", eax\n");//processa o numero por causa do ENTER
                break;
                
            case "escreve":
                codigoFinal.write("\tinvoke GetStdHandle, STD_OUTPUT_HANDLE\n\tmov outputHandle, eax\n");//inicia a saida
                codigoFinal.write("\tinvoke dwtoa, "+ linhaVetor[1] +", addr "+ linhaVetor[1] +"\n"); //Transforma em String pra imprimir
                codigoFinal.write("\tinvoke StrLen, addr "+ linhaVetor[1] +"\n\tmov tamanho, eax\t\n"); //pega o tamanho da String para imprimir
                codigoFinal.write("\tinvoke WriteConsole, outputHandle, addr "+ linhaVetor[1] +", sizeof tamanho, addr console_count, NULL\n\n");//imprime na tela
                codigoFinal.write("\tinvoke ExitProcess, 0\n");
                break;
            case "se":
                /*codigoFinal.write("\t.IF "+linhaVetor[1]+"\n");
                String linha = lerCodigo.readLine();//pega a proxima linha
                linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
                while(!linhaVetor[0].equals("fim_se")){
                    gerarCodigo(linhaVetor);
                    linha = lerCodigo.readLine();//pega a proxima linha
                    linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
                }
                codigoFinal.write("\t.ENDIF "+linhaVetor[1]+"\n");*/
                break;
        }
    }
}
