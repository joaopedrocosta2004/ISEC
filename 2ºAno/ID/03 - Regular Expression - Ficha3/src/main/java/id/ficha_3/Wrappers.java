/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ficha_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author joaos
 */
public class Wrappers {
    static String procura_nome_por_id (String procura) throws FileNotFoundException, IOException {
        String link = "https://eden.dei.uc.pt/~abs/ID/pessoas.html";
        String pesquisa ="";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "pessoas.html");
        
        Scanner ler = new Scanner(new FileInputStream ("pessoas.html"));
        String linha;
        String er = "<tr>\\s*<td\\s*>" + procura + "</td><td\\s*>([a-zA-Z\\s]+)</td>";
        
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        
        ler.close();
        return null;
    }
    
    static String procura_cidade_por_id(String procura) throws FileNotFoundException, IOException {
        String link = "https://eden.dei.uc.pt/~abs/ID/pessoas.html";
        String pesquisa ="";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "pessoas.html");
        
        Scanner ler = new Scanner(new FileInputStream ("pessoas.html"));
        String linha;
        String er = "<tr>\\s*<td\\s*>" + procura + "</td><td\\s*>[a-zA-Z\\s]+</td><td>\\d+</td><td>([a-zA-Z,\\s]+)</td>";
        
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        
        ler.close();
        return null;
    }
    
    static String procura_profissao_por_id(String procura) throws FileNotFoundException, IOException {
        String link = "https://eden.dei.uc.pt/~abs/ID/pessoas.html";
        String pesquisa ="";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "pessoas.html");
        
        Scanner ler = new Scanner(new FileInputStream ("pessoas.html"));
        String linha;
        String er = "<tr>\\s*<td\\s*>" + procura + "</td><td\\s*>[a-zA-Z\\s]+</td><td>\\d+</td><td>[a-zA-Z,\\s]+</td><td>([a-zA-z]+)</td></tr>";
        
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        
        ler.close();
        return null;
    }
    
    static ArrayList procura_nomes(String procura) throws FileNotFoundException, IOException {
        String link = "https://eden.dei.uc.pt/~abs/ID/pessoas.html";
        String pesquisa ="";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "pessoas.html");
        
        Scanner ler = new Scanner(new FileInputStream ("pessoas.html"));
        String linha;
        String er = "(?i)<tr>\\s*<td\\s*>[0-9]+</td><td\\s*>([a-zA-Z\\s]*"+procura+"[a-zA-Z\\s]*)</td><td>";
        
        
        Pattern p = Pattern.compile(er);
        Matcher m;
        ArrayList lista = new ArrayList();
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                lista.add(m.group(1));
            }
        }
        
        ler.close();
        return lista;
    }
}
