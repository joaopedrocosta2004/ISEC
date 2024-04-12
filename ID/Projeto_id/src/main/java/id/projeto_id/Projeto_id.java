/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.projeto_id;

import id.projeto_id.XPathFunctions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Document;

/**
 *
 * @author JoaoSilveira
 */
public class Projeto_id {

    public static void main(String[] args) throws IOException, SaxonApiException {
        Scanner ler = new Scanner(System.in);
        String pesquisa;
        System.out.println("Palavra a procurar: ");
        pesquisa = ler.nextLine();
        Escritor e = criaEscritor(pesquisa);
        e.imprime();
        
        Document doc = XMLJDomFunctions.lerDocumentoXML("escritores.xml");
        //doc = ModeloXML.adicionaEscritor(e, doc);
        
        //doc = ModeloXML.removeEscritor("Pessoa", doc);
        //if (doc != null)
            //XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "escritores.xml");
        
        //doc = ModeloXML.removeOcupacao("Jose Saramago", "serralheiro", doc);
        //if(doc!=null)
        //    XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "escritor.xml");   
        
    }
    
    
     public static Escritor criaEscritor(String escritor) throws IOException, SaxonApiException {
        String xp = "//escritor[@nome='" + escritor+ "']";
        XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
        res = XPathFunctions.executaXpath(xp, "escritores.xml");
        if (res == null || res.size() == 0) { //Escritor não existe
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
            return null;
        
    }
}
