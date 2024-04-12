/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.projeto_id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jdom2.Document;

/**
 *
 * @author JoaoSilveira
 */
public class Projeto_id {

    public static void main(String[] args) throws IOException {
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
        
        doc = ModeloXML.removeOcupacao("Jose Saramago", "serralheiro", doc);
        if(doc!=null)
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "escritor.xml");   
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
