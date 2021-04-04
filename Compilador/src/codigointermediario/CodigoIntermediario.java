package codigointermediario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Esse é o gerador de codigo intermediario que auxilia a geração de codigo final.
 * É transformando a tabela de tokens em um arquivo de codigo intermediario.
 * 
 * @author Eduardo Gonçalves da Silva
 */
public class CodigoIntermediario {
    
    private static FileWriter log; //vai guardada o log
    private static List<String> codigoIntermediario;
    private static BufferedReader lerTabela;
    
    public static void codigoIntermediario(String comandoVetor[]){
        codigoIntermediario = new ArrayList();
        try {
            FileInputStream entrada = new FileInputStream("Tabela.txt"); //abre o arquivo
            InputStreamReader tabela = new InputStreamReader(entrada);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            lerTabela = new BufferedReader(tabela);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
            log = new FileWriter(new File("Log_Intermediario.txt"));//cria o arquivo que vai ser gravado o log semantico
            
            String linha = lerTabela.readLine();//pega a proxima linha
            String linhaVetor[];
            
            linha = lerTabela.readLine();//pega a proxima linha
            System.out.println("===============================================================================");
            System.out.println("Inicia Gerador de Codigo Intermediario:\n");
            do{                
                linhaVetor = linha.split(" ", 3);//separa a frase onde tem espaço e adiciona em um vetor
                gerarCodigo(linhaVetor);
                linha = lerTabela.readLine();                
            }while(linha!=null);
            log.close();
            entrada.close();
            gravarCodigo();
            logIntermediario(comandoVetor);
            System.out.println("Codigo Intermediario: Pronto!!!");
            System.out.println("===============================================================================");
        } catch (FileNotFoundException ex) {
             System.out.println("Erro em abrir o arquivo: " + ex);
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }  
    }
    
    //Imprime o Log Intermediario (onde reconhece os comandos)
    private static void logIntermediario(String comandoVetor[]) throws FileNotFoundException, IOException{
        int i = 1, cont = comandoVetor.length - 2;
        String linha;
        while(i < cont){
            if(comandoVetor[i].equals("-lgc") || comandoVetor[i].equals("-tudo")){
                FileInputStream abreLog = new FileInputStream("Log_Intermediario.txt"); //abre o arquivo
                InputStreamReader log = new InputStreamReader(abreLog);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
                BufferedReader lerLog = new BufferedReader(log);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
       
                linha = lerLog.readLine();//pega a proxima linha
                
                System.out.println("Log Codigo Intermediario:");
                System.out.println("-------------------------------------------------------------------------------");
                while(linha != null){
                    System.out.println(linha);//imprime a linha
                    linha = lerLog.readLine();//pega a proxima linha
                }
                System.out.println("-------------------------------------------------------------------------------");
                abreLog.close();
                imprimeIntermediario();
                break;
            }
            i++;
        }        
    }
    
    //imprime o codigo intermediario
    private static void imprimeIntermediario() throws IOException{
        FileInputStream abreCodigo = new FileInputStream("Codigo_Intermediario.txt"); //abre o arquivo
        InputStreamReader codigo = new InputStreamReader(abreCodigo);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
        BufferedReader lerCodigo = new BufferedReader(codigo);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
        
        String linha = lerCodigo.readLine();//pega a proxima linha
                
        System.out.println("Codigo Intermediario:");
        System.out.println("-------------------------------------------------------------------------------");
        while(linha != null){
            System.out.println(linha);//imprime a linha
            linha = lerCodigo.readLine();//pega a proxima linha
        }
        System.out.println("-------------------------------------------------------------------------------");
        abreCodigo.close();            
    }
    
    //Registra o codigo intermediario em um arquivo
    private static void gravarCodigo() throws FileNotFoundException, IOException{
        FileWriter arquivoIntermediario = new FileWriter(new File("Codigo_Intermediario.txt"));
        for(int i = 0; i<codigoIntermediario.size();i++){
            arquivoIntermediario.write(codigoIntermediario.get(i));
        }
        arquivoIntermediario.close();
    }     
    
    //Gera o codigo intermediario e adiciona em uma lista para ser gravado no arquivo
    public static void gerarCodigo(String linhaVetor[]) throws IOException{
        String linha;
        switch(linhaVetor[0].trim()){
            
            case "tk_ler": //ler nome
                log.write("Reconhece o comando read (tk_ler)\n");
                codigoIntermediario.add("ler ");
                linha = lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                codigoIntermediario.add(linhaVetor[1].trim()+"\n");
                break;
                
            case "tk_escreve": //escreve nome
                log.write("Reconhece o comando write (tk_escreve)\n");
                codigoIntermediario.add("escreve ");
                linha = lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                codigoIntermediario.add(linhaVetor[1].trim()+"\n");
                break;
                
            case "tk_int": //_var nome ou _var nome = num
                log.write("Reconhece o comando int (tk_int)\n");
                codigoIntermediario.add(0,"_var ");           
                linha = lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                codigoIntermediario.add(1,linhaVetor[1].trim());
                linha = lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                if(!linhaVetor[0].equals("tk_ponto_virgula")){                
                    codigoIntermediario.add(2," = ");
                    linha = lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                    codigoIntermediario.add(3,linhaVetor[1].trim()+"\n");
                }else{
                    codigoIntermediario.add(2,"\n");
                }                                   
                break;
                
            case "tk_se": //se obj logico obj 
                log.write("Reconhece o comando if (tk_se)\n");
                codigoIntermediario.add("se ");
                
                linha = lerTabela.readLine();
                linha=lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                
                do{
                    codigoIntermediario.add(linhaVetor[1].trim() + " ");
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_fecha_pa"));
                
                codigoIntermediario.add("\n");
                
                linha=lerTabela.readLine();
                linha=lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                
                do{                   
                    gerarCodigo(linhaVetor);
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_fecha_co"));
                
                codigoIntermediario.add("fim_se\n"); //fim_se             
                break;
            case "tk_senao": // senao
                log.write("Reconhece o comando ifno (tk_senao)\n");
                
                codigoIntermediario.add("senao\n");
                
                linha=lerTabela.readLine();
                linha=lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                
                do{                   
                    gerarCodigo(linhaVetor);
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_fecha_co"));
                
                codigoIntermediario.add("fim_senao\n"); //fim_senao
                break;
            case "id": //x = x + y -> x = x + y       ou        x + y -> x + y      ou       x = x(y+4) - > _reg = y + 4      depois     -> x = x * _reg
                log.write("Reconhece o comando de Expressão (id)\n");
                List<String> aux = new ArrayList();
                                
                do{ 
                    if(linhaVetor[0].trim().equals("tk_atribuicao") || linhaVetor[0].trim().equals("id") || linhaVetor[0].trim().equals("num") || linhaVetor[0].trim().equals("tk_adicao") || linhaVetor[0].trim().equals("tk_subtracao") || linhaVetor[0].trim().equals("tk_multiplicacao") || linhaVetor[0].trim().equals("tk_divisao")){
                        aux.add(linhaVetor[1].trim() + " ");
                    }
                    if(linhaVetor[0].trim().equals("tk_abre_pa")){
                        aux.add("* _reg"); //_reg é registrador
                        codigoIntermediario.add("_reg = ");//Registrador                        
                        linha=lerTabela.readLine();
                        linhaVetor = linha.split(" ",3);
                        do{                                                      
                            codigoIntermediario.add(linhaVetor[1].trim()+ " "); 
                            linha=lerTabela.readLine();
                            linhaVetor = linha.split(" ",3);
                        }while(!linhaVetor[0].trim().equals("tk_fecha_pa"));
                        codigoIntermediario.add("\n");
                    }                    
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_ponto_virgula"));
                aux.add("\n");
                codigoIntermediario.addAll(aux);
                break;
                
            case "tk_enquanto": // enquanto obj logico obj
                log.write("Reconhece o comando while (tk_enquanto)\n");
                codigoIntermediario.add("enquanto ");
                
                linha = lerTabela.readLine();
                linha=lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                
                do{
                    codigoIntermediario.add(linhaVetor[1].trim() + " ");
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_fecha_pa"));
                
                codigoIntermediario.add("\n");
                
                linha=lerTabela.readLine();
                linha=lerTabela.readLine();
                linhaVetor = linha.split(" ",3);
                
                do{                   
                    gerarCodigo(linhaVetor);
                    linha=lerTabela.readLine();
                    linhaVetor = linha.split(" ",3);
                }while(!linhaVetor[0].trim().equals("tk_fecha_co"));
                
                codigoIntermediario.add("fim_enquanto\n");              
                break;
        }
    }       
}
