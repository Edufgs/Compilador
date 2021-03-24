package analisadorsemantico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import static principal.Principal.terminal;

/**
 * Analisador Semantico
 * 
 * @author Eduardo Gonçalves da Silva
 */
public class AnalisadorSemantico {
    private static Map<String, String> variaveis ;
    private static boolean erro;
    
    public static boolean analisadorSemantico(String comandoVetor[]){
        variaveis = new HashMap();
        erro = false;
        try {
            FileInputStream entrada = new FileInputStream("Tabela.txt"); //abre o arquivo
            InputStreamReader tabela = new InputStreamReader(entrada);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            BufferedReader lerTabela = new BufferedReader(tabela);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
            FileWriter log = new FileWriter(new File("Log Semantico.txt"));//cria o arquivo que vai ser gravado o log semantico
            
            String linha = lerTabela.readLine();//pega a proxima linha
            
            //verifica se a tabela dados
            if(linha == null){
                System.out.println("Arquivo da tabela vazia!!!");
                entrada.close();
                log.close();
                terminal();
            }
            
            linha = lerTabela.readLine();//pega a proxima linha
            
            System.out.println("===============================================================================");
            System.out.println("Inicia Analisador Semantico:\n");
            String linhaVetor[];
            do{
                linhaVetor= linha.split(" ", 3);//separa a frase onde tem espaço e adiciona em um vetor
                //se for igual a o erro que aparece na tabela do analisador Lexico, ele pula
                if(linhaVetor[0].equals("Erro")){ 
                    linha = lerTabela.readLine();
                    continue;                
                }
                switch(linhaVetor[0]){
                    case "tk_int":
                        linha = lerTabela.readLine();
                        linhaVetor = linha.split(" ", 3);//separa a frase onde tem espaço e adiciona em um vetor
                        log.write("Verificando variavel \"" + linhaVetor[1].trim()+"\"\n");
                        if(variaveis.get(linhaVetor[1].trim()) != null){                            
                            log.write("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Variavel foi declarada na posição: " + variaveis.get(linhaVetor[1].trim())+ "\n\n");
                            System.out.println("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Variavel \"" + linhaVetor[1].trim() +  "\" foi declarada na posição: " + variaveis.get(linhaVetor[1].trim())+ "\n");
                            erro = true;
                        }else{
                            log.write("Adicionando na memoria variavel \"" + linhaVetor[1].trim()+"\" " + "e local \"" + linhaVetor[2].trim() +"\"\n\n");
                            variaveis.put(linhaVetor[1].trim(), linhaVetor[2].trim());
                        }     
                        break;
                    case "tk_divisao":
                        linha = lerTabela.readLine();
                        linhaVetor = linha.split(" ", 3);//separa a frase onde tem espaço e adiciona em um vetor
                        log.write("Verificando divisão por zero\n");
                        if(linhaVetor[1].trim().equals("0")){
                            log.write("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Não é possivel dividir por zero \"0\"\n\n");
                            System.out.println("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Não é possivel dividir por zero \"0\"\n");
                            erro = true;
                        }else{
                            log.write("Não tem divisão por zero\n\n");
                        }
                        break;
                    case "id":
                        log.write("Verificando id \"" + linhaVetor[1].trim() + "\" foi declarado\n");
                        if(variaveis.get(linhaVetor[1].trim()) == null){
                            log.write("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Variavel \"" + linhaVetor[1].trim()+"\" não declarada\n\n");
                            System.out.println("Erro: Semantico. Posição: "+ linhaVetor[2].trim()  +". Variavel \"" + linhaVetor[1].trim()+"\" não declarada\n");
                            erro = true;
                        }else{
                            log.write("id \"" + linhaVetor[1].trim() + "\" foi declarado\n\n");
                        }
                        break;                        
                }               
                linha = lerTabela.readLine();                
            }while(linha!=null);           
            entrada.close();
            log.close();
            imprimirLog(comandoVetor);
            System.out.println("Analisador Semantico: Pronto");
            System.out.println("\n===============================================================================\n");
        }catch (FileNotFoundException ex1) {
            System.out.println("Erro em abrir o arquivo: " + ex1);
            terminal();
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
            terminal();
        }
        return erro;
    }
    
    //Verifica o comandoVetor e impreme o log se for pedido
     private static void imprimirLog(String comandoVetor[]) throws FileNotFoundException, IOException{
        int i = 1;
        while(i < comandoVetor.length){
            if(comandoVetor[i].equals("-lse") || comandoVetor[i].equals("-tudo")){
                FileInputStream abreLog = new FileInputStream("Log Semantico.txt"); //abre o arquivo
                InputStreamReader Log = new InputStreamReader(abreLog);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
                BufferedReader lerLog = new BufferedReader(Log);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
       
                String linha = lerLog.readLine();//pega a proxima linha
                
                System.out.println("Log Semantico:");
                System.out.println("-------------------------------------------------------------------------------");
                while(linha != null){
                    System.out.println(linha);//imprime a linha
                    linha = lerLog.readLine();//pega a proxima linha
                }
                System.out.print("-------------------------------------------------------------------------------\n");
                abreLog.close();
            }
            i++;
        }      
    }
}
