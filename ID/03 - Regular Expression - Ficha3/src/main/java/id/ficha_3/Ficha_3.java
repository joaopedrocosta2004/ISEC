/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.ficha_3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author joaos
 */
public class Ficha_3 {
    
    static void ficha3a() {
        String telef = "919191919 929992221 91111111111 239494582 9199999999 967779999";
        String er = "\\b9[1236]\\d{7}\\b";
        
        Pattern p = Pattern.compile(er);
        Matcher m = p.matcher(telef);
        
        while(m.find()){
            System.out.println("Numero : " + m.group() + "\n");
        }
        
    }
    
    static void ficha3b(String fileIn, String fileOut) throws FileNotFoundException, IOException{
        
        
        
        
        Scanner input = new Scanner(new FileInputStream(fileIn));
        BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
        
        String regex = "\\b(0[1-9]|[12]\\d|3[01])[-/](0[1-9]|1[0-2])[-/](\\d{4})\\b";
        Pattern p = Pattern.compile(regex);
        Matcher m;
        
        String linha;
        while ((input.hasNextLine())) {
            linha = input.nextLine(); //lê linha a linha
            m = p.matcher(linha);
            while (m.find()) {
                out.write(m.group() + " - data válida \n");
            }
        }
        
        input.close();
        out.close();
        
        System.out.println("Processo concluído. Verifique o arquivo " + fileOut);
        
    }
    
    static void ficha3c(String fileIn, String fileOut) throws FileNotFoundException, IOException {
        
        Scanner input = new Scanner(new FileInputStream(fileIn));
        BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
        
        String regex = "\\b[a-zA-Z]*(ch)[a-zA-Záé]*";
        Pattern p = Pattern.compile(regex);
        Matcher m;
        
        int count = 0;
        String linha;
        String result = null;
        while ((input.hasNextLine())) {
            linha = input.nextLine(); //lê linha a linha
            m = p.matcher(linha);
            while (m.find()) {
                count += 1;
                result = linha.replace(m.group(1), "X");
            }
            out.write(result + "\n");
        }
        out.write("Foram efetuadas " + count + " substituições!\n");
        
        input.close();
        out.close();
        
        System.out.println("Processo concluído. Verifique o arquivo " + fileOut);
        
    }

    public static void main(String[] args) throws IOException {
        //ficha3a();
        //ficha3b("datas.txt", "out.txt");
        //ficha3c("ficheiro3.txt", "out2.txt");
        System.out.println("Introduza o ID de um utilizador: ");
        
        String nome = Wrappers.procura_nome_por_id("111");
        System.out.println("Nome = " + nome + "\n");
        String cidade = Wrappers.procura_cidade_por_id("111");
        System.out.println("Cidade = " + cidade + "\n");
        String profissao = Wrappers.procura_profissao_por_id("111");
        System.out.println("Profissão = " + profissao + "\n");
        
        Scanner ler = new Scanner(System.in);
        String palavra;
        System.out.println("Palavra a procurar: ");
        palavra = ler.nextLine();
        ArrayList lista = Wrappers.procura_nomes(palavra);
        System.out.println(lista);
    }
}
