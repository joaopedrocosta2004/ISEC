/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.projeto_id;

import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author JoaoSilveira
 */
public class ModeloXML {
    
    public static Document adicionaEscritor (Escritor escritor, Document doc) {
        Element raiz;
        if (doc == null) {
            raiz = new Element("lista"); //cria <lista>...</lista>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }
       
        Element esc = new Element("escritor");
        Attribute nome = new Attribute("nome", escritor.getNome());
        esc.setAttribute(nome);
        
        Element filho = new Element ("nome_completo").addContent(escritor.getNome_completo());
        esc.addContent(filho);
        
        filho = new Element ("nacionalidade").addContent(escritor.getNacionalidade());
        esc.addContent(filho);
        
        filho = new Element ("data_nascimento").addContent(escritor.getDnascimento());
        esc.addContent(filho);
        
        filho = new Element ("fotografia").addContent(escritor.getFotografia());
        esc.addContent(filho);
        
        filho = new Element ("ocupacoes");
        esc.addContent(filho);
        
        for (int i = 0; i < escritor.getOcupacoes().size(); i++) {
            Element y = new Element ("ocupacao").addContent(escritor.getOcupacoes().get(i));
            filho.addContent(y);
        }
        
        
        raiz.addContent(esc);
        return doc;
    }
    
    public static Document removeEscritor (String procura, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else
            raiz = doc.getRootElement();
        
        List todos = raiz.getChildren("escritor");
        boolean found = false;
        for(int i = 0; i < todos.size(); i++){
            Element esc = (Element)todos.get(i); //obtem escritor i da Lista
            if (esc.getAttributeValue("nome").contains(procura)) {
                esc.getParent().removeContent(esc);
                found = true;
                System.out.println("Removido");
            }
        }
        if(found == false){
            System.out.println("Não foi encontrado");
            return null;
        }
        return doc;
    }
    
    public static Document removeOcupacao(String escritor, String ocupacao, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else
            raiz = doc.getRootElement();
        
        List todos = raiz.getChildren("escritor");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem escritor i da Lista
            if (esc.getAttributeValue("nome").contains(escritor)) {
                Element ocup = (Element) esc.getChild("ocupacoes");
                List lista2 = ocup.getChildren("ocupacao");
                for (int j = 0; j < lista2.size(); j++) {
                    Element x = (Element) lista2.get(j);
                    if (x.getText().equals(ocupacao)) {
                        x.getParent().removeContent(x);
                        System.out.println("Ocupação removida");
                        found = true;
                    }
                }
            } 
        }
        if(found == false){
            System.out.println("Não foi encontrado");
            return null; 
        }
        return doc;
    }
}
