/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.projeto_id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author joaos
 */
public class Projeto_id {

    public static void main(String[] args) throws IOException {
        Scanner ler = new Scanner(System.in);
        String pesquisa;
        System.out.println("Palavra a procurar: ");
        pesquisa = ler.nextLine();
        Escritor e = criaEscritor(pesquisa);
        e.imprime();
    }
    
    public static Escritor criaEscritor (String pesquisa) throws IOException {
        
        String nome_completo = Wrappers.procura_nome_completo(pesquisa);
        //System.out.println("Nome Completo: " + nome_completo + "\n");
        String nacionalidade = Wrappers.procura_nacionalidade(pesquisa);
        //System.out.println("Nacionalidade: " + nacionalidade + "\n");
        String data_nascimento = Wrappers.procura_data_nascimento(pesquisa);
        //System.out.println("Nascimento: " + data_nascimento + "\n");
        String fotografia = Wrappers.procura_fotografia(pesquisa);
        //System.out.println("Fotogrfia(URL): " + fotografia + "\n");
        ArrayList<String> ocupacoes = Wrappers.procura_ocupacoes(pesquisa);
        //System.out.println("Ocupacoes: " + ocupacoes + "\n");
        
        Escritor Esc = new Escritor(pesquisa, nome_completo, nacionalidade, data_nascimento, fotografia, ocupacoes);
        return Esc;
    }
    
}
