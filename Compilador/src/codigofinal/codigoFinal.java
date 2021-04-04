package codigofinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Esse é o gerador de codigo final. 
 * 
 * @author Eduardo Gonçalves da Silva
 */
public class codigoFinal {
    
    private static BufferedReader lerCodigo; //lê o codigo intermediario
    private static FileWriter codigoFinal; //arquivo de codigo final
    private static FileWriter logFinal; //arquivo log do codigo final
    private static String aux;
            
    public static void codigoFinal(String comandoVetor[]){
        try{       
            FileInputStream abreCodigo = new FileInputStream("Codigo_Intermediario.txt"); //abre o arquivo
            InputStreamReader codigo= new InputStreamReader(abreCodigo);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            lerCodigo = new BufferedReader(codigo);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
      
            codigoFinal = new FileWriter(new File(comandoVetor[comandoVetor.length-1]));
            logFinal = new FileWriter(new File("Log_Codigo_Final.txt"));
            
            System.out.println("===============================================================================");
            System.out.println("Inicia Gerador de Codigo Final:\n");
            
            iniciaArquivoFinal();//Inicia com as bibliotecas e outros 
            
            String linha = lerCodigo.readLine();//pega a proxima linha
            String linhaVetor[];
            
            while(linha!=null){
                linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor                
                gerarCodigo(linhaVetor);
                linha = lerCodigo.readLine();//pega a proxima linha
            }
            codigoFinal.write("\tinvoke ExitProcess, 0\n");
            codigoFinal.write("\n\tend start\n");
            logFinal.write("Finaliza Codigo Final\n");
            
            abreCodigo.close();
            codigoFinal.close();
            logFinal.close();
            System.out.println("Codigo Final: Pronto!!!");
            System.out.println("===============================================================================");
            logFinal(comandoVetor);
        }catch (FileNotFoundException ex) {
             System.out.println("Erro em abrir o arquivo: " + ex);
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }            
    }
    
    //Imprime o log codigo final (onde reconhece os comandos)
    private static void logFinal(String comandoVetor[]) throws FileNotFoundException, IOException{
        int i = 1, cont = comandoVetor.length - 2;
        String linha;
        while(i < cont){
            if(comandoVetor[i].equals("-lgc") || comandoVetor[i].equals("-tudo")){
                FileInputStream abreLog = new FileInputStream("Log_Codigo_Final.txt"); //abre o arquivo
                InputStreamReader log = new InputStreamReader(abreLog);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
                BufferedReader lerLog = new BufferedReader(log);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
       
                linha = lerLog.readLine();//pega a proxima linha
                
                System.out.println("Log Codigo Final:");
                System.out.println("-------------------------------------------------------------------------------");
                while(linha != null){
                    System.out.println(linha);//imprime a linha
                    linha = lerLog.readLine();//pega a proxima linha
                }
                System.out.println("-------------------------------------------------------------------------------");
                abreLog.close();
                imprimeFinal(comandoVetor);
                break;
            }
            i++;
        }        
    }
    
    //imprime o codigo Final
    private static void imprimeFinal(String comandoVetor[]) throws IOException{
        FileInputStream abreCodigo = new FileInputStream(comandoVetor[comandoVetor.length-1]); //abre o arquivo
        InputStreamReader codigo = new InputStreamReader(abreCodigo);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
        BufferedReader lerCodigo = new BufferedReader(codigo);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
        
        String linha = lerCodigo.readLine();//pega a proxima linha
                
        System.out.println("Codigo Final:");
        System.out.println("-------------------------------------------------------------------------------");
        while(linha != null){
            System.out.println(linha);//imprime a linha
            linha = lerCodigo.readLine();//pega a proxima linha
        }
        System.out.println("-------------------------------------------------------------------------------");
        abreCodigo.close();            
    }
    
    private static void iniciaArquivoFinal() throws IOException{
        logFinal.write("Inicia Codigo Final\n");
        codigoFinal.write(".386\n.model flat, stdcall\noption casemap: none\n");
        codigoFinal.write("\ninclude \\masm32\\include\\windows.inc\ninclude \\masm32\\include\\user32.inc\ninclude \\masm32\\include\\kernel32.inc\nincludelib \\masm32\\lib\\user32.lib\nincludelib \\masm32\\lib\\kernel32.lib\n");
        codigoFinal.write("include \\masm32\\include\\masm32.inc\nincludelib \\masm32\\lib\\masm32.lib\n");
        codigoFinal.write("\n.data\n");
        codigoFinal.write("\tpulalinha db 13,10 , 0\n");
    }
    
    private static void gerarCodigo(String linhaVetor[]) throws IOException{
        int i;
        String linha;     
        switch(linhaVetor[0]){
            case "_var":
                aux = "";
                logFinal.write("Insere as VARIAVEIS:\n");
                do{
                    logFinal.write("Variavel: "+ linhaVetor[1]+"\n");
                    codigoFinal.write("\t"+linhaVetor[1]+" dd 10 DUP(0)\n");//aloca bytes de armazenamento
                    if(linhaVetor.length == 4){//se estiver criando a variavel inserindo um numero
                        aux+="\tmov " + linhaVetor[1]+", "+linhaVetor[3]+"\n";
                    }
                    
                     linha = lerCodigo.readLine();//pega a proxima linha
                     linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
                                          
                }while(linhaVetor[0].equals("_var"));
                codigoFinal.write("\n.code\n");//inicia a parte do codigo
                codigoFinal.write("\tstart:\n");//começa o programa
                codigoFinal.write(aux);
                gerarCodigo(linhaVetor);
                break;
            case "ler":
                logFinal.write("Insere o comando LER\n");
                codigoFinal.write("\tinvoke StdIn, addr "+ linhaVetor[1]+",500\n");//le do console
                codigoFinal.write("\tinvoke StripLF, addr "+linhaVetor[1]+"\n");//Remove a sujeira do ENTER (CRLF)
                codigoFinal.write("\tinvoke atodw, addr "+ linhaVetor[1]+"\n");//Transforma a String em numero
                codigoFinal.write("\tmov "+linhaVetor[1]+", eax\n");//move para variavel novamente
                break;
                
            case "escreve":
                logFinal.write("Insere o comando ESCREVER\n");
                codigoFinal.write("\tinvoke dwtoa, "+linhaVetor[1]+", addr "+linhaVetor[1]+"\n");
                codigoFinal.write("\tinvoke StdOut, addr "+linhaVetor[1]+"\n");//imprime no console
                codigoFinal.write("\tinvoke StdOut, addr pulalinha\n"); //pula linha
                //Volta de novo para numero (Padrão que eu escolhi)
                codigoFinal.write("\tinvoke atodw, addr "+ linhaVetor[1]+"\n");//Transforma a String em numero
                codigoFinal.write("\tmov "+linhaVetor[1]+", eax\n");//move para variavel novamente
                break;
            case "se":
                logFinal.write("Inicia o comando SE\n");
                codigoFinal.write("\tmov eax,"+ linhaVetor[1]+"\n");
                i = 2;
                codigoFinal.write("\t.IF eax");                
                while(i<4){
                    codigoFinal.write(" " + linhaVetor[i]);
                    i++;
                }
                codigoFinal.write("\n");                                              
                break;
            case "enquanto":
                logFinal.write("Inicia comando ENQUANTO\n");
                aux = "\tmov eax,"+ linhaVetor[1]+"\n";
                codigoFinal.write(aux);
                i=2;
                codigoFinal.write("\t.WHILE eax");                
                while(i<4){
                    codigoFinal.write(" " + linhaVetor[i]);
                    i++;
                }
                codigoFinal.write("\n");                               
                break;
            case "senao":
                    codigoFinal.write("\t.ELSE\n");
                break;
            case "fim_se": case "fim_senao":
                    linha = lerCodigo.readLine();//pega a proxima linha
                    linhaVetor= linha.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
                    if(!linhaVetor[0].equals("senao")){
                        codigoFinal.write("\t.ENDIF\n");
                        logFinal.write("Finaliza comando SE\n");
                    }
                    gerarCodigo(linhaVetor);               
                break;
            case "fim_enquanto":
                    codigoFinal.write(aux);
                    codigoFinal.write("\t.ENDW\n");
                    logFinal.write("Finaliza comando ENQUANTO\n");
                break;
            default:
                logFinal.write("Inicia EXPRESSÃO\n");
                i = linhaVetor.length;
                if(i <5){ //Verifica se é uma variavel recebendo um valor  

                    if(linhaVetor[1].equals("=")){
                        codigoFinal.write("\tmov "+linhaVetor[0]+", "+linhaVetor[2]+"\n");
                    }else{
                        expressao(linhaVetor);//chama metodo que monta as expressões
                    }                    
                }else{//Se for uma expressão
                    if(linhaVetor[0].equals("_reg")){
                        linhaVetor[0] = "edx";
                    }else if(linhaVetor[i-1].equals("_reg"))
                        linhaVetor[i-1] = "edx";
                    expressao(linhaVetor);//chama metodo que monta as expressões                    
                }
                logFinal.write("Finaliza EXPRESSÃO\n");
        }
    }
    
    private static void expressao(String linhaVetor[]) throws IOException {
        int i = linhaVetor.length;
        switch (linhaVetor[i-2]){ //verifica qual conta
                        case "+":
                            codigoFinal.write("\tmov eax, "+linhaVetor[i-1]+"\n");//move o ultimo numero/variavel para o registrador
                            codigoFinal.write("\tadd eax, "+linhaVetor[i-3]+"\n");
                            codigoFinal.write("\tmov "+linhaVetor[0]+", eax\n");
                            break;
                        case "-":
                            codigoFinal.write("\tmov eax, "+linhaVetor[i-3]+"\n");//move o ultimo numero/variavel para o registrador
                            codigoFinal.write("\tsub eax, "+linhaVetor[i-1]+"\n");
                            codigoFinal.write("\tmov "+linhaVetor[0]+", eax\n");
                            break;
                        case "*":
                            codigoFinal.write("\tmov eax, "+linhaVetor[i-1]+"\n");//move o ultimo numero/variavel para o registrador
                            codigoFinal.write("\tmov ebx," + linhaVetor[i-3]+ "\n");
                            codigoFinal.write("\tmul ebx\n");
                            codigoFinal.write("\tmov "+linhaVetor[0]+", eax\n");
                            break;
                        case "/":
                            codigoFinal.write("\tmov eax, "+linhaVetor[i-1]+"\n");//move o ultimo numero/variavel para o registrador
                            codigoFinal.write("\tmov ebx, "+linhaVetor[i-1]+"\n");//move o ultimo numero/variavel para o registrador
                            codigoFinal.write("\tmov eax," + linhaVetor[i-3]+ "\n");
                            codigoFinal.write("\tdiv ebx\n");
                            codigoFinal.write("\tmov "+linhaVetor[0]+", eax\n");
                            break;
                    }
    }
}
