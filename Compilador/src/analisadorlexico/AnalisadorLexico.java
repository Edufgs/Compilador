package analisadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static principal.Principal.terminal;

/**
 * Analisador Lexico
 * @author Eduardo Gonçalves da Silva
 */
public class AnalisadorLexico {
    private static boolean erro;
    //E:\Eduardo\Eduardo\Semestres\6 Semestre\Compiladores\Projetos\AnalisadorLexico\
    /* Para iniciar o compilador tem que: "compilador -lt endereco com o nome do arquivo de codigo
        Sem o -lt não é imprimido a tabela
    */
    public static boolean analisadorLexico(String comandoVetor[]) {
        int coluna; //Marca a posição no vetor
        int l = 0;//marca a linha
        char linhaChar[]; //vai guardar as linha em vetor de char
        erro = false;
        
        try{//Tratamento de Erro
            FileInputStream entrada = new FileInputStream(comandoVetor[comandoVetor.length-1]); //abre o arquivo
            InputStreamReader codigo = new InputStreamReader(entrada);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
            BufferedReader lerCodigo = new BufferedReader(codigo);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
            
            //abrindo arquivo para ser gravado a tabela            
            FileWriter tabela = new FileWriter(new File("Tabela.txt"));
            tabela.write("Tokens \t\t\tLexemas \t\t\tPosição "); //grava o inicio da tabela no arquivo
            
            //le o codigo digitado
            String linha = lerCodigo.readLine(); //pega a primeira linha
            String lexema;
            
            //verifica se o arquivo esta vazio
            if(linha == null){
                System.out.println("Arquivo de codigo vazio!!!");
                entrada.close();
                tabela.close();
                terminal();
            }
            System.out.println("===============================================================================");
            System.out.println("Inicia Analisador Lexico:");
            //começa a leitura do codigo
            while(linha != null){ //Enquanto houver linha no arquivo
                coluna = 0;//coluna começa no inicio
                linha += ' ';//adiciona espaço para saber o final da linha
                lexema = "";//limpa a String que guarda os lexemas para imprimir na tabela
                linhaChar = linha.toCharArray(); //Transforma a String em vetor de char
                q0(linhaChar,coluna,l,lexema,tabela); //Começa na posição zero
                linha = lerCodigo.readLine(); //pega a proxima linha
                l++;//vai para proxima linha
            }
            entrada.close();//fecha o arquivo fonte
            tabela.close();//fecha a tabela 
            System.out.println("");
            
            //imprime a tabela se for pedida
            tabelaToken(comandoVetor);
            System.out.println("\nAnalisador Lexico: Pronto!!!");
            System.out.println("===============================================================================\n");
        }catch(FileNotFoundException ex){
            System.out.println("Erro em abrir o arquivo: " + ex);
            terminal();
        }catch(IOException e){
            System.out.println("Erro:" + e);
            terminal();
        }
        return erro;
    }

    private static void tabelaToken(String comandoVetor[]) throws FileNotFoundException, IOException{
        int i = 1;
        String linha;
        while(i < comandoVetor.length){
            if(comandoVetor[i].equals("-lt") || comandoVetor[i].equals("-tudo")){
                FileInputStream abreTabela = new FileInputStream("Tabela.txt"); //abre o arquivo
                InputStreamReader tabelab = new InputStreamReader(abreTabela);//abre para leituda do arquivo (ele lê bytes e os decodifica em caracteres usando um especificado charset)
                BufferedReader lerTabela = new BufferedReader(tabelab);//Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para fornecer uma leitura eficiente de caracteres, matrizes e linhas
       
                linha = lerTabela.readLine();//pega a proxima linha
                System.out.println("Tabela:");
                System.out.println("-------------------------------------------------------------------------------");
                while(linha != null){
                    System.out.println(linha);//imprime a linha
                    linha = lerTabela.readLine();//pega a proxima linha
                }
                System.out.println("-------------------------------------------------------------------------------");
                abreTabela.close();
            }
            i++;
        }
    }
    //Estado inicial
    private static void q0(char[] vetor, int coluna, int l, String lexema, FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 's':
                lexema += 's'; 
                coluna++;
                q1(vetor,coluna,l,lexema,tabela);
                break;
            case 'e':
                lexema += 'e'; 
                coluna++;
                q6(vetor,coluna,l,lexema, tabela);
                break;
            case 'r':
                lexema += 'r'; 
                coluna++;
                q14(vetor,coluna,l,lexema, tabela);
                break;
            case 'w':
                lexema += 'w'; 
                coluna++;
                q18(vetor,coluna,l,lexema, tabela);
                break;
            case 'i':
                lexema += 'i'; 
                coluna++;
                q23(vetor,coluna,l,lexema, tabela);               
                break;
            case '/':
                lexema += '/'; 
                coluna++;
                q36(vetor,coluna,l,lexema, tabela);  
                break;
            case '*':
                lexema += '*'; 
                coluna++;
                q35(vetor,coluna,l,lexema, tabela);  
                break;
            case '-':
                lexema += '-'; 
                coluna++;
                q34(vetor,coluna,l,lexema, tabela);
                break;
            case '+':
                lexema += '+'; 
                coluna++;
                q33(vetor,coluna,l,lexema, tabela);
                break;
            case ';':
                lexema += ';'; 
                coluna++;
                q13(vetor,coluna,l,lexema, tabela);
                break;
            case ')':
                lexema += ')'; 
                coluna++;
                q12(vetor,coluna,l,lexema, tabela);
                break;
            case '(':
                lexema += '('; 
                coluna++;
                q11(vetor,coluna,l,lexema, tabela);
                break;
            case ']':
                lexema += ']'; 
                coluna++;
                q10(vetor,coluna,l,lexema, tabela);
                break;
            case '[':
                lexema += '['; 
                coluna++;
                q9(vetor,coluna,l,lexema, tabela);
                break;
            case '>':
                lexema += '>'; 
                coluna++;
                q37(vetor,coluna,l,lexema, tabela);
                break;
            case '<':
                lexema += '<'; 
                coluna++;
                q38(vetor,coluna,l,lexema, tabela);
                break;
            case '=':
                lexema += '='; 
                coluna++;
                q39(vetor,coluna,l,lexema, tabela);
                break;
            case ' ':
                if(coluna + 1 != vetor.length){
                    coluna++;
                    q0(vetor,coluna,l,lexema, tabela);
                }
                break;
            case '\t'://reconhecer tabulação
                coluna++;
                q0(vetor,coluna,l,lexema, tabela);
                break;
            default:
                switch(verifChar(vetor,coluna)){
                    case 1: 
                        lexema += vetor[coluna];
                        coluna++;
                        q40(vetor, coluna,l,lexema, tabela);
                        break;
                    case 2:
                        q41(vetor,coluna,l,lexema, tabela);
                        break;
                    case 3:
                        //imprime na tela
                        System.out.print("Erro Lexico \t\t ");
                        System.out.print("Caractere não esperado: " + vetor[coluna]);
                        System.out.print("\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                        System.out.println("");
                        erro = true;
                        
                        //imprimir no arquivo
                        tabela.write("\nErro Lexico \t\t ");
                        tabela.write("Caractere não esperado: " + vetor[coluna]);
                        tabela.write("\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                        lexema="";
                        coluna++;
                        q0(vetor,coluna,l,lexema, tabela);
                    break;
                    default:
                        System.out.println("Erro q0");
                        erro = true;
                }
        }
    }
    
    //verifica numero, letra ou caractere especial
    private static int verifChar(char[] vetor, int coluna){        
        if (Character.isDigit(vetor[coluna])) {
            return 1;
        } else if (Character.isLetter(vetor[coluna])) {
            return 2;
        } else {
            return 3;
        }
    }
    
    //estado do comando start (t)
    private static void q1(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
         switch(vetor[coluna]){
            case 't':
                lexema+= 't';
                coluna++;
                q2(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando start (a)
    private static void q2(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
         switch(vetor[coluna]){
            case 'a':
                lexema+= 'a';
                coluna++;
                q3(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando start (r)
    private static void q3(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
         switch(vetor[coluna]){
            case 'r':
                lexema+= 'r';
                coluna++;
                q4(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando start (t)
    private static void q4(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
         switch(vetor[coluna]){
            case 't':
                lexema+= 't';
                coluna++;
                q5(vetor, coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado final do comando start. Onde imprime na tela e verifica se é o comando "start"
    private static void q5(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 5://verifica se é o tokens start ou um id com final start
                        tabela.write("\ntk_inicio \t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q5");
                erro = true;
        }
    }
    
     //estado do comando end (n)
    private static void q6(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'n':
                lexema+= 'n';
                coluna++;
                q7(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
   
    //estado do comando end (d)
    private static void q7(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'd':
                lexema+= 'd';
                coluna++;
                q8(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
   
    //estado final do comando end. Onde imprime na tela e verifica se é o comando "end"
    private static void q8(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 3://verifica se é o tokens end ou um id com final end
                        tabela.write("\ntk_fim \t\t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length()));
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q8");
                erro = true;
        }
    }
    
     //estado final do comando [
    private static void q9(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_abre_co \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);   
    }
    
     //estado final do comando ]
    private static void q10(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_fecha_co \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);         
    }
    
     //estado final do comando (
    private static void q11(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_abre_pa \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);        
    }
    
     //estado final do comando )
    private static void q12(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_fecha_pa \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);      
    }
    
    //estado final do comando ;
    private static void q13(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_ponto_virgula \t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);          
    }
    
    //estado do comando read (e)
    private static void q14(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'e':
                lexema+= 'e';
                coluna++;
                q15(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando read (a)
    private static void q15(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'a':
                lexema+= 'a';
                coluna++;
                q16(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando read (d)
    private static void q16(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'd':
                lexema+= 'd';
                coluna++;
                q17(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado final do comando read. Onde imprime na tela e verifica se é o comando "read"
    private static void q17(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 4://verifica se é o tokens read ou um id com final read
                        tabela.write("\ntk_ler \t\t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q17");
                erro = true;
        }
    }
    
    //estado do comando write (r)
    private static void q18(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'r':
                lexema+= 'r';
                coluna++;
                q19(vetor,coluna,l,lexema,tabela);
                break;
            case 'h':
                lexema+= 'h';
                coluna++;
                q29(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando write (i)
    private static void q19(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'i':
                lexema+= 'i';
                coluna++;
                q20(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando write (t)
    private static void q20(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 't':
                lexema+= 't';
                coluna++;
                q21(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando write (e)
    private static void q21(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'e':
                lexema+= 'e';
                coluna++;
                q22(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado final do comando write. Onde imprime na tela e verifica se é o comando "write"
    private static void q22(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 5://verifica se é o tokens write ou um id com final write
                        tabela.write("\ntk_escreve \t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q22");
                erro = true;
        }
    }
    
     //estado do comando int, ou if e ifno
    private static void q23(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'n':
                lexema+= 'n';
                coluna++;
                q24(vetor,coluna,l,lexema,tabela);
                break;
            case 'f':
                lexema+= 'f';
                coluna++;
                q26(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado do comando int (t)
    private static void q24(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 't':
                lexema+= 't';
                coluna++;
                q25(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
     //estado final do comando int. Onde imprime na tela e verifica se é o comando "int"
    private static void q25(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 3://verifica se é o tokens int ou um id com final int
                        tabela.write("\ntk_int \t\t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q25");
                erro = true;
        }
    }
    //estado final do comando if. Onde imprime na tela e verifica se é o comando "if"
    private static void q26(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                switch(vetor[coluna]){//verifica se é o ifno
                    case 'n':
                        lexema+= 'n';
                        coluna++;
                        q27(vetor,coluna,l,lexema,tabela);
                        break;
                    default:
                        q0(vetor, coluna,l,lexema,tabela);
                }
                break;
            case 3:
                switch(lexema.length()){
                    case 2://verifica se é o tokens if ou um id com final if
                        tabela.write("\ntk_se \t\t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q26");
                erro = true;
        }
    }
    
     //estado do comando ifno (o)
    private static void q27(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'o':
                lexema+= 'o';
                coluna++;
                q28(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
    //estado final do comando ifno. Onde imprime na tela e verifica se é o comando "ifno"
    private static void q28(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 4://verifica se é o tokens ifno ou um id com final ifno
                        tabela.write("\ntk_senao \t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q28");
                erro = true;
        }
    }
     //estado do comando while (i)
    private static void q29(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'i':
                lexema+= 'i';
                coluna++;
                q30(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
     //estado do comando while  (l)
    private static void q30(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'l':
                lexema+= 'l';
                coluna++;
                q31(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
     //estado do comando while  (e)
    private static void q31(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(vetor[coluna]){
            case 'e':
                lexema+= 'e';
                coluna++;
                q32(vetor,coluna,l,lexema,tabela);
                break;
            default:
                q41(vetor,coluna,l,lexema,tabela);
        }
    }
    
     //estado final do comando while. Onde imprime na tela e verifica se é o comando "while"
    private static void q32(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                q0(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                switch(lexema.length()){
                    case 5://verifica se é o tokens write ou um id com final write
                        tabela.write("\ntk_enquanto \t\t");
                        tabela.write(lexema);
                        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
       
                        lexema = "";
                        q0(vetor,coluna,l,lexema,tabela);
                    break;
                    default:
                     q41(vetor,coluna,l,lexema,tabela);
                }
                break;
            default:
                System.out.println("Erro no q32");
                erro = true;
        }
    }
    
    //estado final do comando +
    private static void q33(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_adicao \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);             
    }
    //estado final do comando -
    private static void q34(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_subtracao \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);          
    }
    
    //estado final do comando *
    private static void q35(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_multiplicacao \t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);        
    }
    
    //estado final do comando /
    private static void q36(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_divisao \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);            
    }
    
    //estado final do comando >
    private static void q37(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_maior \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);             
    }
    
      //estado final do comando <
    private static void q38(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_menor \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);         
    }
    
      //estado final do comando =
    private static void q39(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{       
        tabela.write("\ntk_atribuicao \t\t");
        tabela.write(lexema);
        tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da
        lexema = "";
        q0(vetor,coluna,l,lexema,tabela);
                    
    }
    
     //estado do num. Verifica se é um num e imprime na tela
    private static void q40(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: 
                lexema += vetor[coluna];
                coluna++;
                q40(vetor, coluna,l,lexema,tabela);
                break;
            case 2:
                //imprime na tela
                System.out.print("Erro Id não pode começar com numero!!!!");
                System.out.println("\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                erro = true;
                //imprime na tabela
                tabela.write("\nErro Id não pode começar com numero!!!!");
                tabela.write("\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                break;
            case 3:
                tabela.write("\nnum \t\t\t");
                tabela.write(lexema);
                tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                lexema="";
                q0(vetor,coluna,l,lexema,tabela);
                break;
            default:
                System.out.println("Erro q40");
                erro = true;
        }
    }  
   
    //estado do id. Verifica se é um id e imprime na tela
    private static void q41(char[] vetor, int coluna, int l, String lexema,FileWriter tabela) throws IOException{
        switch(verifChar(vetor,coluna)){
            case 1: case 2:
                lexema += vetor[coluna];
                coluna++;
                q41(vetor, coluna,l,lexema,tabela);
                break;
            case 3:
                tabela.write("\nid \t\t\t");
                tabela.write(lexema);
                tabela.write("\t\t\t\t Linha " + l + " Coluna " + (coluna - lexema.length())); //diminui o tanto de caractere para mostar o começo da palavra
                lexema="";
                q0(vetor,coluna,l,lexema,tabela);
                break;
            default:
                System.out.println("Erro q41");
                erro = true;
        }
    }  
}
