/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.projeto_id;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author JoaoSilveira
 */
public class Wrappers {
    
    static String obtem_nome_completo(String escritor) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, escritor, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String linha;
        String er1 = "Nome completo";
        String er2 = "left;\">([a-zA-Z\\sóíéçãõ]+)";
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                linha = ler.nextLine(); //<td> lixo
                linha = ler.nextLine(); //procura er2
                m2 = p2.matcher(linha);
                if (m2.find()){
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        ler.close();
        return null;
    }
    
    static String obtem_nacionalidade(String escritor) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, escritor, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String linha;
        String er1 = "Nacionalidade";
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
                //"title=\"[a-zA-Z\\éíóçãõêâ]+\">([a-zA-Z\\éíóçãõê]+)</a>";
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                linha = ler.nextLine(); //<td> lixo
                linha = ler.nextLine(); //procura er2
                m2 = p2.matcher(linha);
                if (m2.find()){
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        ler.close();
        return null;
    }
    
    static String obtem_dnascimento(String escritor) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, escritor, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String linha;
        String er1 = "Nascimento";
        String er2 = ">([^<]+)<";
                //"left;\">([0-9]{2}\\s[a-z]{2}\\s[a-zA-Z\\ç]+\\s[a-zA-Z]{2}\\s[0-9]{4})<br\\s/>";
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                linha = ler.nextLine(); //<td> lixo
                linha = ler.nextLine(); //procura er2
                m2 = p2.matcher(linha);
                if (m2.find()){
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        ler.close();
        return null;
    }
    
    static String obtem_fotografia(String escritor) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, escritor, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String linha;
        String er1 = "image\" content=\"([^\"]+)\">";
        
        Pattern p1 = Pattern.compile(er1);
        Matcher m1;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                return m1.group(1);   
            }
        }
        ler.close();
        return null;
    }
    
    static ArrayList<String> obtem_ocupacoes(String escritor) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
 
        ArrayList<String> lista = new ArrayList();
        
        HttpRequestFunctions.httpRequest1(link, escritor, "escritor.html");
        
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String linha;
        String er1 = "Ocupação";
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                linha = ler.nextLine(); //<td> lixo
                linha = ler.nextLine(); //procura er2
                m2 = p2.matcher(linha);
                while (m2.find()){
                    lista.add(m2.group(1));
                }
            }
        }
        ler.close();
        return lista;
    }
    
    public static ArrayList<String> obtem_ocupacoes2(String escritor) throws FileNotFoundException, IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", escritor, "escritor.html");
        ArrayList<String> lista = new ArrayList();
        String er1 = "Ocupação";
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
        String er3 ="<ul><li>";
        Matcher m1, m2,m3;
        Pattern p1, p2,p3;
        String linha = null;
        p1 = Pattern.compile(er1);
        p2 = Pattern.compile(er2);
        p3 = Pattern.compile(er3);
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                do{
                   linha = ler.nextLine();
                   m3 = p3.matcher(linha);
                }while(!m3.find());
                //linha = ler.nextLine();
                m2 = p2.matcher(linha);
                while (m2.find()) {
                    lista.add(m2.group(1));
                    linha = ler.nextLine();
                    m2 = p2.matcher(linha);
                }
            }
        }
        ler.close();
        return lista;
    }
}
