/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.projeto_id;

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
    static String procura_nome_completo (String pesquisa) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream ("escritor.html"));
        String linha;
        String er1 = "Nome completo"; // Marcador Nome Completo
        String er2 = "left;\">([a-zA-Z\\sóíéçãõ]+)"; // Nome Completo
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        
        Matcher m1,m2;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                linha = ler.nextLine(); // <td> lixo
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if(m2.find()) {
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        
        ler.close();
        return null;
    }
    
    static String procura_nacionalidade (String pesquisa) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream ("escritor.html"));
        String linha;
        String er1 = "Nacionalidade"; // Marcador Nacionalidade
        String er2 = "title=\"[^\"]+\">([^<]+)</a>"; // Nacionalidade 
        // outra er = "title=\"[a-zA-Z\\\\sóíéçãõê]+\">([a-zA-Z\\\\sóíéçãõê]+)</a>"
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        
        Matcher m1,m2;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                linha = ler.nextLine(); // <td> lixo
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if(m2.find()) {
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        
        ler.close();
        return null;
    }
    
    static String procura_data_nascimento (String pesquisa) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream ("escritor.html"));
        String linha;
        String er1 = "Nascimento"; // Marcador Nascimento
        String er2 = ">([^<]+)<";
                //"left;\">([0-9]{2}\\s[a-z]{2}\\s[a-zA-Z\\ç]+\\s[a-zA-Z]{2}\\s[0-9]{4})<br\\s/>";
        
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        
        Matcher m1,m2;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                linha = ler.nextLine(); // <td> lixo
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if(m2.find()) {
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        
        ler.close();
        return null;
    }
    
    static String procura_fotografia (String pesquisa) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream ("escritor.html"));
        String linha;
        String er1 = "image\" content=\"([^\"]+)\">"; // Marcador Imagem
        
        
        Pattern p1 = Pattern.compile(er1);
        
        
        Matcher m1;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                return m1.group(1);
            }
        }
        
        ler.close();
        return null;
    }
    
    
    static ArrayList<String> procura_ocupacoes (String pesquisa) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        
        ArrayList<String> lista = new ArrayList();
        
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream ("escritor.html"));
        String linha;
        String er1 = "Ocupação"; // Marcador Ocupação
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
        
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        
        Matcher m1,m2;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                linha = ler.nextLine(); // <td> lixo
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                while (m2.find()) {
                    lista.add(m2.group(1));
                }
            }
        }
        
        ler.close();
        return lista;
    }
}
