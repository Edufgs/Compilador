package analisadorsintatico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import static principal.Principal.terminal;

/**
 * Analisador Sintatico
 * @author Eduardo Gonçalves da Silva
 */

public class AnalisadorSintatico {
    
    private static List<String> pilha; //Pilha para guardar as produções
    private static List<linhaTabela> listaTokens; //Guarda a lista de tokens do codigo
    private static FileWriter log; //vai guardada o log de operações (empilha, desempilha, redução, etc....)
    
    public static void analisadorSintatico(String comandoVetor[]) {
        pilha = new ArrayList(); //inicia a pilha que guarda as produções
        listaTokens = new ArrayList();
            
        //Adiciona os simbolos no fnal da lista e pilha
        pilha.add("$");
        pilha.add(0,"PROGRAMA"); //adiciona o primeiro para comaçar na pilha de simbolo terminal
       
        try {
            FileInputStream entrada = new FileInputStream("Tabela.txt"); //abre o arquivo
            InputStreamReader tabela = new InputStreamReader(entrada);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            BufferedReader lerTabela = new BufferedReader(tabela);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
            
            log = new FileWriter(new File("Log.txt"));//cria o arquivo que vai ser gravado o log sintatico
            
            String linha = lerTabela.readLine();//pega a proxima linha
            
            //verifica se o arquivo esta vazio
            if(linha == null){
                System.out.println("Arquivo da tabela vazia!!!");
                entrada.close();
                log.close();
                terminal();
            }
            linha = lerTabela.readLine();//pega a proxima linha
            
            System.out.println("Inicia Analisador Sintatico:");
            
            //Preenche a Lista de simbolos não terminais
            while(linha!=null){
                String linhaVetor[] = linha.split(" ", 3);//separa a frase onde tem espaço e adiciona em um vetor
                //se for igual a o erro que aparece na tabela do analisador Lexico, ele pula
                if(linhaVetor[0].equals("Erro")){ 
                    linha = lerTabela.readLine();
                    continue;                
                }
                listaTokens.add(new linhaTabela(linhaVetor[0].trim(),linhaVetor[linhaVetor.length-1]));
                linha = lerTabela.readLine();
            }
            
            entrada.close();
            
            listaTokens.add(new linhaTabela("$"," "));
            
            while(!pilha.isEmpty() && !listaTokens.isEmpty()){
                if(pilha.get(0).equals(listaTokens.get(0).getToken())){
                    log.write("\n++++++++++++++++++++++++");
                    desempilha(pilha.get(0));
                    log.write("\nRemove "+listaTokens.get(0).getToken());
                    listaTokens.remove(0);
                    log.write("\n++++++++++++++++++++++++");
                }else{
                    tabelaSintatica(comandoVetor);
                }
            }
            
            log.close();
            
            imprimirLog(comandoVetor);
            System.out.println("\n");
            System.out.println("Analisador Sintatico:Pronto!!!!");
        } catch (FileNotFoundException ex) {
            System.out.println("Erro em abrir o arquivo: " + ex);
            terminal();
        }catch(IOException ex){
            System.out.println("Erro: " + ex.toString());
            terminal();
        }

    }
    
     private static void imprimirLog(String comandoVetor[]) throws FileNotFoundException, IOException{
        int i = 1;
        while(i < comandoVetor.length){
            if(comandoVetor[i].equals("-ls") || comandoVetor[i].equals("-tudo")){
                FileInputStream abreLog = new FileInputStream("Log.txt"); //abre o arquivo
                InputStreamReader Log = new InputStreamReader(abreLog);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
                BufferedReader lerLog = new BufferedReader(Log);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
       
                String linha = lerLog.readLine();//pega a proxima linha
                while(linha != null){
                    System.out.println(linha);//imprime a linha
                    linha = lerLog.readLine();//pega a proxima linha
                }
                abreLog.close();
            }
            i++;
        }
        
    }
    
    //Entra para fazer a comparação na tabela Sintatica
    private static void tabelaSintatica(String comandoVetor[]) throws IOException{
        switch(pilha.get(0)){//coluna
            case "PROGRAMA":
                switch(listaTokens.get(0).getToken()){ //linha
                    case "tk_inicio":
                        producao(0);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um tk_inicio mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }                
                break; 
            case "LISTA_COMANDOS": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_fecha_co":
                        producao(2);
                        break;
                    case "tk_ler": case "tk_escreve": case "tk_int": case "id":
                        producao(1);
                        break;
                    case "tk_se": case "tk_senao":
                        producao(3);
                        break;
                    case "tk_enquanto":
                        producao(4);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " + listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "COMANDO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_ler":
                        producao(6);
                        break;
                    case "tk_escreve":
                        producao(5);
                        break;
                    case "tk_int":
                        producao(10);
                        break;
                    case "id":
                        producao(18);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "VARIAVEL": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_int":
                        producao(9);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um tk_int mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "TIPO_VARIAVEL": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_int":
                        producao(13);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um tk_int mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "EXPRESSAO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "id":
                        producao(14);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um id mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "OPERACAO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_adicao":
                        producao(22);
                        break;
                    case "tk_subtracao":
                       producao(23);
                       break;
                    case "tk_multiplicacao":
                        producao(24);
                        break;
                    case "tk_divisao":
                        producao(25);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "CONDICAO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_se":
                        producao(19);
                        break;
                    case "tk_senao":
                        producao(20);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "REPETICAO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_enquanto":
                        producao(21);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um tk_enquanto mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "LOGICO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_maior":
                        producao(26);
                        break;
                    case "tk_menor":
                        producao(27);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado algo logico (> ou <) mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "BLOCO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_abre_co":
                        producao(28);
                        break;
                    case "tk_fecha_co":
                        producao(29);
                        break;
                    case "tk_abre_pa":
                        producao(30);
                        break;
                    case "tk_fecha_pa":
                        producao(31);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Era esperado um bloco \"[, ], ( ou )\" mas foi encontrado um "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "ATRIBUIR": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_abre_pa": case "tk_ponto_virgula": case "tk_adicao": case "tk_subtracao": case "tk_multiplicacao": case "tk_divisao":
                        producao(12);
                        break;
                    case "tk_atribuicao":
                        producao(11);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "OBJETO": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "id":
                        producao(8);
                        break;
                    case "num":
                        producao(7);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            case "CONTA": 
                switch(listaTokens.get(0).getToken()){//linha
                    case "tk_abre_pa":
                        producao(16);
                        break;
                    case "tk_fecha_pa": case "tk_ponto_virgula":
                        producao(17);
                        break;
                    case "tk_adicao": case "tk_subtracao": case "tk_multiplicacao": case "tk_divisao":
                        producao(15);
                        break;
                    default:
                        System.out.println("Erro: Sintatico. Não era esperado "+listaTokens.get(0).getToken() + " " +listaTokens.get(0).getLocal());
                        log.close();
                        imprimirLog(comandoVetor);
                        terminal();
                }
                break;
            default:
                System.out.println("Erro na Tabela Sintatica!!!!");
                log.close();
                imprimirLog(comandoVetor);
                terminal();
        }
    }
    
    //faz a produçao adicionando e retirando na pilha
    private static void producao(int n){
        try{
            switch(n){
                case 0:
                    log.write("\n=============================");
                    desempilha("PROGRAMA");
                    empilha("tk_fim");
                    empilha("tk_fecha_co");
                    empilha("LISTA_COMANDOS");
                    empilha("tk_abre_co");
                    empilha("tk_inicio");
                    log.write("\n=============================");
                    break;
                case 1:
                    log.write("\n=============================");                    
                    desempilha("LISTA_COMANDOS");
                    empilha("LISTA_COMANDOS");
                    empilha("tk_ponto_virgula");
                    empilha("COMANDO");
                    log.write("\n=============================");
                    break;
                case 2:
                    log.write("=============================");
                    desempilha("LISTA_COMANDOS");
                    log.write("\nEmpilha Ê"); //vazio
                    log.write("\n=============================");
                    break;                    
                case 3:
                    log.write("\n=============================");
                    desempilha("LISTA_COMANDOS");
                    empilha("LISTA_COMANDOS");
                    empilha("CONDICAO");
                    log.write("\n=============================");
                    break;
                 case 4:
                    log.write("\n=============================");
                    desempilha("LISTA_COMANDOS");
                    empilha("LISTA_COMANDOS");
                    empilha("REPETICAO");
                    log.write("\n=============================");
                    break;
                case 5:
                    log.write("\n=============================");
                    desempilha("COMANDO");
                    empilha("id");
                    empilha("tk_escreve");
                    log.write("\n=============================");
                    break;
                case 6:
                    log.write("\n=============================");
                    desempilha("COMANDO");
                    empilha("id");
                    empilha("tk_ler");
                    log.write("\n=============================");
                    break;
                case 7:
                    log.write("\n=============================");
                    desempilha("OBJETO");
                    empilha("num");                 
                    log.write("\n=============================");
                    break;
                case 8:
                    log.write("\n=============================");
                    desempilha("OBJETO");
                    empilha("id");              
                    log.write("\n=============================");
                    break;
                case 9:
                    log.write("\n=============================");
                    desempilha("VARIAVEL");
                    empilha("ATRIBUIR");
                    empilha("id");                 
                    empilha("TIPO_VARIAVEL");
                    log.write("\n=============================");
                    break;
                case 10:
                    log.write("\n=============================");
                    desempilha("COMANDO");
                    empilha("VARIAVEL");
                    log.write("\n=============================");
                    break;
                case 11:
                    log.write("\n=============================");
                    desempilha("ATRIBUIR");
                    empilha("OBJETO");
                    empilha("tk_atribuicao");
                    log.write("\n=============================");
                    break;
                case 12:
                    log.write("\n=============================");
                    desempilha("ATRIBUIR");
                    log.write("\nEmpilha Ê");//vazio
                    log.write("\n=============================");
                    break;
                case 13:
                    log.write("\n=============================");
                    desempilha("TIPO_VARIAVEL");
                    empilha("tk_int");
                    log.write("\n=============================");
                    break;
                case 14:
                    log.write("\n=============================");
                    desempilha("EXPRESSAO");
                    empilha("CONTA");
                    empilha("ATRIBUIR");
                    empilha("id");
                    log.write("\n=============================");
                    break;
                case 15:
                    log.write("\n=============================");
                    desempilha("CONTA");
                    empilha("OBJETO");
                    empilha("OPERACAO");
                    log.write("\n=============================");
                    break;
                case 16:
                    log.write("\n=============================");
                    desempilha("CONTA");
                    empilha("CONTA");
                    empilha("tk_fecha_pa");
                    empilha("CONTA");
                    empilha("OBJETO");
                    empilha("tk_abre_pa");
                    log.write("\n=============================");
                    break;
                case 17:
                    log.write("\n=============================");
                    desempilha("CONTA");
                    log.write("\nEmpilha Ê"); //vazio
                    log.write("\n=============================");
                    break;
                case 18:
                    log.write("\n=============================");
                    desempilha("COMANDO");
                    empilha("EXPRESSAO");
                    log.write("\n=============================");
                    break;
                case 19:
                    log.write("\n=============================");
                    desempilha("CONDICAO");
                    empilha("tk_fecha_co");
                    empilha("LISTA_COMANDOS");
                    empilha("tk_abre_co");
                    empilha("tk_fecha_pa");
                    empilha("id");
                    empilha("LOGICO");
                    empilha("id");
                    empilha("tk_abre_pa");
                    empilha("tk_se");
                    log.write("\n=============================");
                    break;
                case 20:
                    log.write("\n=============================");
                    desempilha("CONDICAO");
                    empilha("tk_fecha_co");
                    empilha("LISTA_COMANDOS");
                    empilha("tk_abre_co");
                    empilha("tk_senao");
                    log.write("\n=============================");
                    break;
                case 21:
                    log.write("\n=============================");
                    desempilha("REPETICAO");
                    empilha("tk_fecha_co");
                    empilha("LISTA_COMANDOS");
                    empilha("tk_abre_co");
                    empilha("tk_fecha_pa");
                    empilha("OBJETO");
                    empilha("LOGICO");
                    empilha("id");
                    empilha("tk_abre_pa");
                    empilha("tk_enquanto");
                    log.write("\n=============================");
                    break;
                case 22:
                    log.write("\n=============================");
                    desempilha("OPERACAO");
                    empilha("tk_adicao");
                    log.write("\n=============================");
                    break;
                case 23:
                    log.write("\n=============================");
                    desempilha("OPERACAO");
                    empilha("tk_subtracao");
                    log.write("\n=============================");
                    break;
                case 24:
                    log.write("\n=============================");
                    desempilha("OPERACAO");
                    empilha("tk_multiplicacao");
                    log.write("\n=============================");
                    break;
                case 25:
                    log.write("\n=============================");
                    desempilha("OPERACAO");
                    empilha("tk_divisao");
                    log.write("\n=============================");
                    break;
                case 26:
                    log.write("\n=============================");
                    desempilha("LOGICO");
                    empilha("tk_maior");
                    log.write("\n=============================");
                    break;
                case 27:
                    log.write("\n=============================");
                    desempilha("LOGICO");
                    empilha("tk_menor");
                    log.write("\n=============================");
                    break;
                case 28:
                    log.write("\n=============================");
                    desempilha("BLOCO");
                    empilha("tk_abre_co");
                    log.write("\n=============================");
                    break;
                case 29:
                    log.write("\n=============================");
                    desempilha("BLOCO");
                    empilha("tk_fecha_co");
                    log.write("\n=============================");
                    break;
                case 30:
                    log.write("\n=============================");
                    desempilha("BLOCO");
                    empilha("tk_abre_pa");
                    log.write("\n=============================");
                    break;
                case 31:
                    log.write("\n=============================");
                    desempilha("BLOCO");
                    empilha("tk_fecha_pa");
                    log.write("\n=============================");
                    break;
                default:
                    System.out.println("Erro na produçao!!!!");
                    terminal();
            }
        }catch(IOException ex){
            try {
                System.out.println("Erro ao escrever no arquivo Log: "+ex.toString());
                log.close();
                terminal();
            } catch (IOException exl) {
                System.out.println("Erro ao fechar o arquivo Log: "+exl.toString());
            }
        }
        
    }
    
    //metodo para desempilhar
    public static void desempilha(String nome) throws IOException{
        pilha.remove(0);
        log.write("\nDesempilha "+nome);
    }
    
    //metodo para empilha
    public static void empilha(String nome) throws IOException{
        pilha.add(0,nome);
        log.write("\nEmpilha "+nome);
    }    
}
