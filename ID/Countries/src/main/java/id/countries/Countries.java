/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.countries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;


public class Countries {

    public static void main(String[] args) throws IOException {
        Scanner ler = new Scanner(System.in);
        String pesquisa;
        System.out.println("Palavra a procurar: ");
        pesquisa = ler.nextLine();
        System.out.println("Nome do País: " + Wrappers.obtem_nome(pesquisa));
        System.out.println("Código ISO País: " + Wrappers.obtem_ISO(pesquisa));
        System.out.println("Capital: " + Wrappers.obtem_capital(pesquisa));
        System.out.println("Continente: " + Wrappers.obtem_continente(pesquisa));
        System.out.println("Moeda: " + Wrappers.obtem_moeda(pesquisa));
        System.out.println("População: " + Wrappers.obtem_populacao(pesquisa));
        System.out.println("Crescimento da População: " + Wrappers.obtem_crescimento_populacao(pesquisa));
        System.out.println("Área: " + Wrappers.obtem_area(pesquisa));
        System.out.println("Cidades mais Populosas: " + Wrappers.obtem_cidades_populosas(pesquisa));
        ArrayList<String> lista = Wrappers.obtem_idiomas_oficiais(pesquisa);
        System.out.println("Idiomas Oficiais: " + Wrappers.obtem_idiomas_oficiais(pesquisa));
        if (lista.isEmpty()) {
            lista = Wrappers.obtem_idiomas_oficiais2(pesquisa);
            System.out.println("Idiomas Oficiais: " + Wrappers.obtem_idiomas_oficiais2(pesquisa));
        }
        System.out.println("Países Vizinhos: " + Wrappers.obtem_paises_vizinhos(pesquisa));
        System.out.println("Presidente da República: " + Wrappers.obtem_presidente_republica(pesquisa));
        System.out.println("Domínio Internet: " + Wrappers.obtem_dominio_internet(pesquisa));
        System.out.println("Imagem: " + Wrappers.obtem_imagem(pesquisa));
    }
    
    public static Countrie criaPais(String pais) throws IOException, SaxonApiException {
        String xp = "//countrie[@nome='" + pais + "']";
        XdmValue res = XPathFunctions.executaXpath(xp, "countries.xml");
        res = XPathFunctions.executaXpath(xp, "countries.xml");
        if (res == null || res.size() == 0) { //Pais nao existe
            String nome = Wrappers.obtem_nome(pais);
            String ISO = Wrappers.obtem_ISO(pais);
            String continente = Wrappers.obtem_continente(pais);
            String presidente_republica = Wrappers.obtem_presidente_republica(pais);
            String imagem = Wrappers.obtem_imagem(pais);
            
            

            Countrie a = new Countrie (nome, ISO, continente, presidente_republica, imagem);
            return a;
        }
            return null;
        
    }
    
    public static Facto criaFacto(String pais) throws IOException, SaxonApiException {
        String xp = "//facto[@nome='" + pais + "']";
        XdmValue res = XPathFunctions.executaXpath(xp, "factos.xml");
        res = XPathFunctions.executaXpath(xp, "factos.xml");
        if (res == null || res.size() == 0) { //Pais nao existe
            String nome = Wrappers.obtem_nome(pais);
            String ISO = Wrappers.obtem_ISO(pais);
            String continente = Wrappers.obtem_continente(pais);
            String presidente_republica = Wrappers.obtem_presidente_republica(pais);
            String imagem = Wrappers.obtem_imagem(pais);
            String capital = Wrappers.obtem_capital(pais);
            String moeda = Wrappers.obtem_moeda(pais);
            String populacao = Wrappers.obtem_populacao(pais);
            String crescimento_populacao = Wrappers.obtem_crescimento_populacao(pais);
            String area = Wrappers.obtem_area(pais);
            ArrayList<String> cidades_populosas = Wrappers.obtem_cidades_populosas(pais);
            String dominio_internet = Wrappers.obtem_dominio_internet(pais);
            ArrayList<String> lista1 = Wrappers.obtem_idiomas_oficiais(pais);
            if (lista1.isEmpty()) {
                lista1 = Wrappers.obtem_idiomas_oficiais2(pais);
            }
            ArrayList<String> lista2 = Wrappers.obtem_paises_vizinhos(pais);
            

            Facto a = new Facto (nome, ISO, capital, continente, moeda, populacao, crescimento_populacao, area, cidades_populosas,  lista1, lista2, presidente_republica, dominio_internet, imagem);
            return a;
        }
            return null;
        
    }
}
