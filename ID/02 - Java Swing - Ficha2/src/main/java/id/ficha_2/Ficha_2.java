/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.ficha_2;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author joaos
 */
public class Ficha_2 {
    static String lerFicheiro(String nomeF) throws FileNotFoundException {
        // Exercicio 4.1
        String linha;
        StringBuilder texto = new StringBuilder();
        Scanner input = new Scanner (new FileInputStream(nomeF));
        
        while (input.hasNextLine()) {
            linha = input.nextLine(); //lê linha a linha
            texto.append(linha).append("\n");
        }
        
        input.close();
        return texto.toString();
    }
    
    static void separaFicheiro(String nomeF) throws IOException {
        Scanner ler = new Scanner(new FileInputStream(nomeF));
        String linha;
        String []campos;
        BufferedWriter homens = new BufferedWriter(new FileWriter("alunosH.txt"));
        BufferedWriter mulheres = new BufferedWriter(new FileWriter("alunosM.txt"));
        
        while ((ler.hasNextLine())) {
            linha = ler.nextLine(); //lê linha a linha
            campos =  linha.split(";");
            if (campos[2].equals("F")){
                mulheres.write(campos[0] + "\n");
            } else if (campos[2].equals("M")) {
                homens.write(campos[0] + "\n");
            }
        }
        ler.close();
        homens.close();
        mulheres.close();
    }
    
    static double calcula_MediaIdade(String genero) throws FileNotFoundException {
        
        Scanner input = new Scanner(new FileInputStream("alunos.txt"));
        int soma = 0;
        int conta = 0;
        while ((input.hasNextLine())) {
            String linha = input.nextLine(); //lê linha a linha
            String []campos = linha.split(";");
            
            if (campos[2].equals(genero)){
                soma += Integer.parseInt(campos[3]);
                conta ++;
            }
        }
        input.close();
        return soma / conta;
}

    public static void main(String[] args) {
        Frame app = new Frame();
        app.setVisible(true);
    }
}
