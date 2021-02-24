
package principal;

import static analisadorlexico.AnalisadorLexico.analisadorLexico;
import static analisadorsintatico.AnalisadorSintatico.analisadorSintatico;
import static java.lang.System.exit;
import java.util.Scanner;

/**
 * Menu onde vai ler o comando e executar os analizadores e outros
 * @author Edurado Gonçalves da Silva
 */
public class Principal {
    public static void main(String[] args) {
        terminal();
        
    }
    
    //Terminal para digitar o comando do compilador
    public static void terminal(){
        System.out.println("Terminal:");
        Scanner scann = new Scanner(System.in);
        String comando = scann.nextLine();
        String comandoVetor[] = comando.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
        System.out.println("");
        
        //verifica se está correto o comando
        if(comandoVetor[0].equals("sair")){
            exit(0);
        }else if(comandoVetor[0].equals("compilador") == false){
            System.out.println("Comando não é reconhecido!!!");
            terminal();
        }else{
            analisadorLexico(comandoVetor);
           analisadorSintatico(comandoVetor);
            terminal();
        }
    }
}
