
package principal;

import static analisadorlexico.AnalisadorLexico.analisadorLexico;
import static analisadorsemantico.AnalisadorSemantico.analisadorSemantico;
import static analisadorsintatico.AnalisadorSintatico.analisadorSintatico;
import static codigofinal.codigoFinal.codigoFinal;
import static codigointermediario.CodigoIntermediario.codigoIntermediario;
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
        boolean erro=false;
        System.out.println("Terminal:");
        Scanner scann = new Scanner(System.in);
        String comando = scann.nextLine();
        String comandoVetor[] = comando.split(" ");//separa a frase onde tem espaço e adiciona em um vetor
        System.out.println("");
        
        //verifica se está correto o comando
        if(comandoVetor.length == 1 && comandoVetor[0].equals("sair")){
            exit(0);
        }else if(!comandoVetor[0].equals("compilador")){
            System.out.println("Comando não é reconhecido!!!");
            terminal();
        }else{
            String aux[] = comando.split("-");
            if((comandoVetor.length - aux.length) != 2){
                System.out.println("Comando não é reconhecido!!!");
                terminal();
            }
            if(analisadorLexico(comandoVetor)){
                erro = true;
            } 
            if(analisadorSintatico(comandoVetor)){
                erro = true;
            }
            if(analisadorSemantico(comandoVetor)){
                erro = true;
            }
            if(erro == true){
                System.out.println("Houve alguns erros.");
                System.out.println("Codigo Intermediario não iniciado");
            }else{
                codigoIntermediario(comandoVetor);
                codigoFinal(comandoVetor);
            }
            terminal();
        }
    }
}
