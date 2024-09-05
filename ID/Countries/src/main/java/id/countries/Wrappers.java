/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.countries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Juliana
 */
public class Wrappers {

    static String obtem_nome(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "<span class=\"mw-page-title-main\">([A-Za-z\\s]+)</span>";
        
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

    static String obtem_ISO(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "title=\"ISO 3166-2:[A-Z]{2}\">([A-Z]{2})</a>";
        
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

    static String obtem_capital(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "class=\"infobox-data\"><a href=\"/wiki/[A-Za-z0-9\\_áí%]+\" title=\"[A-Za-z\\sáí]+\">([A-Za-z\\sáí]+)</a";
        
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

    static String obtem_continente(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "style=\"display:none\">[^<]*?([A-Za-z]+)</div>";;
     
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

    static String obtem_moeda(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";
 
        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Currency\\s*</td>";
        String er2 = "([A-Za-z\\s)(]+)\\s*</td>";

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
                    return m2.group(1).trim();
                }
            }
        }
        ler.close();
        return null;
    }

    static String obtem_populacao(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";
 
        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Population\\s*</td>";
        String er2 = "([0-9,]+)\\s*</td>";
        
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

    static String obtem_crescimento_populacao(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";
 
        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Population Growth Rate\\s*</td>";
        String er2 = "\\s*([0-9\\.%]+)\\s*";
        
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

    static String obtem_area(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";
 
        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "([0-9,]+)&#160;km<sup>2</sup>";
        
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

    static ArrayList<String> obtem_cidades_populosas(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";

        ArrayList<String> lista = new ArrayList();

        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");

        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Population in Major Urban Areas\\s*</td>";
        String er2 = "([A-Za-z\\s]+)(\\s*\\(capital\\))?\\s*([\\d.]+ million)";

        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                linha = ler.nextLine(); //<td> lixo
                linha = ler.nextLine(); //procura er2
                m2 = p2.matcher(linha);
                // Enquanto encontrar para er2, adiciona à lista
                while (m2.find()) {
                    lista.add(m2.group(1).trim());
                }
            }
        }
        ler.close();
        return lista;
    }

    static ArrayList<String> obtem_idiomas_oficiais(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";

        ArrayList<String> lista = new ArrayList();

        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");

        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Official(?:&#160;|\\s)?languages?<\\/th><td class=\"infobox-data\"><a href=\"[^\"]+\" title=\"[^\"]+\">([^<]+)<\\/a>";

        Pattern p1 = Pattern.compile(er1);
        Matcher m1;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                lista.add(m1.group(1));
            }
        }

        ler.close();
        return lista;
    }

    static ArrayList<String> obtem_idiomas_oficiais2(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";
 
        ArrayList<String> lista = new ArrayList();
 
        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "Predominant Language\\s*</td>";
        String er2 = "([A-Za-z]+)\\s*\\(official";

         
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1, m2;
        
        while (ler.hasNextLine()) {
        linha = ler.nextLine();
        m1 = p1.matcher(linha);
        if (m1.find()) {
            linha = ler.nextLine(); //<td> lixo
            linha = ler.nextLine(); //procura er2
            m2 = p2.matcher(linha);
            // Enquanto encontrar para er2, adiciona à lista
            while (m2.find()) {
                lista.add(m2.group(1).trim());
            }
        }
    }
        
        ler.close();
        return lista;
    }

    static ArrayList<String> obtem_paises_vizinhos(String countrie) throws FileNotFoundException, IOException {
        String link = "https://www.countryreports.org/country/";
        
        ArrayList<String> lista = new ArrayList();
 
        HttpRequestFunctions.httpRequest1(link, countrie + ".htm", "countrie.html");
        
        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = ">Border Countries: </a>([^<]+)";
        
        Pattern p1 = Pattern.compile(er1);
        Matcher m1;
        
        while(ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()){
                lista.add(m1.group(1));   
            }
        }
        ler.close();
        return lista;
    }
    
    
    static String obtem_presidente_republica(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";

        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");

        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = ">President</a> </div></th><td class=\"infobox-data\">[a-z\\s\"=<>]*<a href=\"/wiki/[A-Za-z0-9\\_á%çã-]+\" title=\"[A-Za-z\\sáçã-]+\">([A-Za-z\\sáçã-]+)</a>";

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

    static String obtem_dominio_internet(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";

        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");

        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "<a href=\"/wiki/[a-z.]{3}\" title=\"[a-z.]{3}\">([a-z.]{3})</a>";

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

    static String obtem_imagem(String countrie) throws FileNotFoundException, IOException {
        String link = "https://en.wikipedia.org/wiki/";

        HttpRequestFunctions.httpRequest1(link, countrie, "countrie.html");

        Scanner ler = new Scanner(new FileInputStream("countrie.html"));
        String linha;
        String er1 = "image\" content=\"([^\"]+)\">";

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

}
