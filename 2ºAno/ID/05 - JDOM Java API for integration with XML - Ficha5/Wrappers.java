/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ficha4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;

/**
 *
 * @author anabela
 */
public class Wrappers {

    public static String obtem_nome_completo(String escritor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", escritor, "escritor.html");
        String er1 = "Nome completo";
        String er2 = "[^\"]+\">(.*)";
        Matcher m1, m2;
        Pattern p1, p2;
        p1 = Pattern.compile(er1);
        p2 = Pattern.compile(er2);

        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                ler.nextLine();
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if (m2.find()) {
                    ler.close();
                    return m2.group(1);
                }
            }
        }
        ler.close();
        return escritor;
    }

    public static String obtem_nacionalidade(String escritor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", escritor, "escritor.html");
        String er1 = "Nacionalidade";
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
        String er3 = "<td style=\"vertical-align: top; text-align: left;\">(.*)";
        
        Matcher m1, m2,m3;
        Pattern p1, p2,p3;
        p1 = Pattern.compile(er1);
        p2 = Pattern.compile(er2);
        p3 = Pattern.compile(er3);
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                ler.nextLine();
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if (m2.find()) {
                    ler.close();
                    return m2.group(1);
                }
                m3 = p3.matcher(linha);
                if (m3.find()) {
                    ler.close();
                    return m3.group(1);
                }
            }
        }
        ler.close();
        return null;
    }

    public static String obtem_dnascimento(String escritor) throws IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", escritor, "escritor.html");
        String er1 = "Nascimento";
        String er2 = "left;\">([^<]+)<br />";
        String er3 = "<a href=\"[^\"]+\" title=\"[^\"]+\">([^<]+)</a>( de )<a href=\"[^\"]+\" title=\"[0-9]+\">([0-9]+)</a>";

        Matcher m1, m2, m3;
        Pattern p1, p2, p3;
        p1 = Pattern.compile(er1);
        p2 = Pattern.compile(er2);
        p3 = Pattern.compile(er3);
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                ler.nextLine();
                linha = ler.nextLine();
                m2 = p2.matcher(linha);
                if (m2.find()) {
                    ler.close();
                    return m2.group(1);
                } else {
                    m3 = p3.matcher(linha);
                    if (m3.find()) {
                        ler.close();
                        return m3.group(1) + m3.group(2) + m3.group(3);
                    }
                }

            }
        }
        ler.close();
        return null;
    }

    public static String obtem_fotografia(String pesquisa) throws IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "escritor.html");
        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        String er1 = "image\" content=\"([^\"]+)\">";
        Pattern p1 = Pattern.compile(er1);
        Matcher m1;
        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                ler.close();
                return m1.group(1);
            }

        }
        ler.close();
        return null;
    }

    public static ArrayList<String> obtem_ocupacoes(String escritor) throws FileNotFoundException, IOException {
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", escritor, "escritor.html");
        ArrayList<String> lista = new ArrayList();
        String er1 = "Ocupação";
        String er2 = "title=\"[^\"]+\">([^<]+)</a>";
        Matcher m1, m2;
        Pattern p1, p2;
        p1 = Pattern.compile(er1);
        p2 = Pattern.compile(er2);

        Scanner ler = new Scanner(new FileInputStream("escritor.html"));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            m1 = p1.matcher(linha);
            if (m1.find()) {
                ler.nextLine();
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
    public static Escritor criaEscritor(String escritor) throws IOException {
        String nome_completo = Wrappers.obtem_nome_completo(escritor);
        String nacionalidade = Wrappers.obtem_nacionalidade(escritor);
        String nascimento = Wrappers.obtem_dnascimento(escritor);
        String foto = Wrappers.obtem_fotografia(escritor);
        ArrayList<String> lista = Wrappers.obtem_ocupacoes(escritor);
        if (lista.isEmpty()) {
            lista = Wrappers.obtem_ocupacoes2(escritor);
        }

        Escritor a = new Escritor(escritor, nome_completo, nacionalidade, nascimento, foto, lista);
        return a;

        
    }
}

