/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.countries;

import java.util.List;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author joaos
 */
public class ModeloXML {

    public static Document adicionaPaises(Countrie countrie, Document doc) throws SaxonApiException {
        Element raiz;

        if (doc == null) {
            raiz = new Element("countries"); //cria <lista>...</lista>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
            String xp = "//countrie[@nome ='" + countrie.getNome() + "']";
            XdmValue res = XPathFunctions.executaXpath(xp, "countries.xml");

            if (res != null && res.size() > 0) {
                System.out.println("O pais " + countrie.getNome() + " já existe!");
                return null;
            } else {
                Element esc = new Element("countrie");

                Attribute nome = new Attribute("nome", countrie.getNome());
                esc.setAttribute(nome);

                Element filho = new Element("iso").addContent(countrie.getISO());
                esc.addContent(filho);

                filho = new Element("continente").addContent(countrie.getContinente());
                esc.addContent(filho);

                filho = new Element("presidente_republica").addContent(countrie.getPresidente_republica());
                esc.addContent(filho);

                filho = new Element("imagem").addContent(countrie.getImagem());
                esc.addContent(filho);

                raiz.addContent(esc);
            }
        }

        return doc;
    }

    public static Document adicionaFactos(Facto facto, Document doc) throws SaxonApiException {
        Element raiz;

        if (doc == null) {
            raiz = new Element("factos"); //cria <lista>...</lista>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
            String xp = "//facto[@nome ='" + facto.getNome() + "']";
            XdmValue res = XPathFunctions.executaXpath(xp, "factos.xml");

            if (res != null && res.size() > 0) {
                System.out.println("O pais " + facto.getNome() + " já existe!");
                return null;
            } else {
                Element esc = new Element("facto");

                Attribute nome = new Attribute("nome", facto.getNome());
                esc.setAttribute(nome);

                Element filho = new Element("iso").addContent(facto.getISO());
                esc.addContent(filho);

                filho = new Element("continente").addContent(facto.getContinente());
                esc.addContent(filho);

                filho = new Element("presidente_republica").addContent(facto.getPresidente_republica());
                esc.addContent(filho);

                filho = new Element("imagem").addContent(facto.getImagem());
                esc.addContent(filho);

                filho = new Element("capital").addContent(facto.getCapital());
                esc.addContent(filho);

                filho = new Element("moeda").addContent(facto.getMoeda());
                esc.addContent(filho);

                filho = new Element("populacao").addContent(facto.getPopulacao());
                esc.addContent(filho);

                filho = new Element("crescimento_populacao").addContent(facto.getCrescimento_populacao());
                esc.addContent(filho);

                filho = new Element("area").addContent(facto.getArea());
                esc.addContent(filho);

                Element idiomas = new Element("idiomas_oficiais");
                for (String idioma : facto.getIdiomas_oficiais()) {
                    idiomas.addContent(new Element("idioma").addContent(idioma));
                }
                esc.addContent(idiomas);

                Element cidades = new Element("cidades_populosas");
                for (String cidade : facto.getCidades_populosas()) {
                    cidades.addContent(new Element("cidade").addContent(cidade));
                }
                esc.addContent(cidades);

                Element vizinhos = new Element("paises_vizinhos");
                for (String vizinho : facto.getPaises_vizinhos()) {
                    vizinhos.addContent(new Element("pais_vizinho").addContent(vizinho));
                }
                esc.addContent(vizinhos);

                filho = new Element("dominio_internet").addContent(facto.getDominio_internet());
                esc.addContent(filho);

                raiz.addContent(esc);
            }
        }

        return doc;
    }

    public static Document removeCountrie(String procura, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List<Element> todos = raiz.getChildren("countrie");
            boolean found = false;
            for (Element esc : todos) {
                if (esc.getAttributeValue("nome").contains(procura)) {
                    esc.getParent().removeContent(esc);
                    found = true;
                    System.out.println("Removido");
                }
            }
            if (!found) {
                System.out.println("Não foi encontrado");
                return null;
            }
        }
        return doc;
    }

    public static Document removeFactos(String procura, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List<Element> todos = raiz.getChildren("facto");
            boolean found = false;
            for (Element esc : todos) {
                if (esc.getAttributeValue("nome").contains(procura)) {
                    esc.getParent().removeContent(esc);
                    found = true;
                    System.out.println("Removido");
                }
            }
            if (!found) {
                System.out.println("Não foi encontrado");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraCapital(String countrie, String novoCapital, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").equals(countrie)) {
                String capital = esc.getChildText("capital");
                System.out.println("Capital " + capital);
                esc.getChild("capital").setText(novoCapital);
                System.out.println("Capital alterada com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Pais " + countrie + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraPopulacao(String countrie, String novoPopulacao, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").equals(countrie)) {
                String populacao = esc.getChildText("populacao");
                System.out.println("Populacao " + populacao);
                esc.getChild("populacao").setText(novoPopulacao);
                System.out.println("Populacao alterada com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Pais " + countrie + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraIdiomas_oficial(String countrie, String antigoIdioma, String novoIdioma, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar a informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); // Obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").contains(countrie)) {
                Element ocup = (Element) esc.getChild("idiomas_oficiais");
                List lista_oc = ocup.getChildren("idioma");
                for (int j = 0; j < lista_oc.size(); j++) {
                    Element o = (Element) lista_oc.get(j);
                    if (o.getText().equals(antigoIdioma)) {
                        o.setText(novoIdioma); // Altera o texto da ocupação
                        System.out.println("Idioma alterado com sucesso!");
                        found = true;
                        break; // Sai do loop assim que a alteração for feita
                    }
                }
            }
            if (found) {
                break; // Sai do loop principal assim que a alteração for feita
            }
        }
        if (!found) {
            System.out.println("Pais " + countrie + " ou cidade populosa " + antigoIdioma + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraArea(String countrie, String novoArea, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").equals(countrie)) {
                String area = esc.getChildText("area");
                System.out.println("Area  " + area);
                esc.getChild("area").setText(novoArea);
                System.out.println("Area alterada com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Pais " + countrie + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraMoeda(String countrie, String novoMoeda, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").equals(countrie)) {
                String moeda = esc.getChildText("moeda");
                System.out.println("Moeda " + moeda);
                esc.getChild("moeda").setText(novoMoeda);
                System.out.println("Moeda alterada com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Pais " + countrie + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraCidades_populosas(String countrie, String antigaCidade_populosa, String novoCidade_populosa, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar a informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); // Obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").contains(countrie)) {
                Element ocup = (Element) esc.getChild("cidades_populosas");
                List lista_oc = ocup.getChildren("cidade");
                for (int j = 0; j < lista_oc.size(); j++) {
                    Element o = (Element) lista_oc.get(j);
                    if (o.getText().equals(antigaCidade_populosa)) {
                        o.setText(novoCidade_populosa); // Altera o texto da ocupação
                        System.out.println("Cidade alterada com sucesso!");
                        found = true;
                        break; // Sai do loop assim que a alteração for feita
                    }
                }
            }
            if (found) {
                break; // Sai do loop principal assim que a alteração for feita
            }
        }
        if (!found) {
            System.out.println("Pais " + countrie + " ou cidade populosa " + antigaCidade_populosa + " não foi encontrado");
            return null;
        }
        return doc;
    }

    public static Document alteraPaises_Vizinhos(String countrie, String antigoPais, String novoPais, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro nao existe - nao dá para alterar a informação");
            return null;
        } else {
            raiz = doc.getRootElement();
        }
        List todos = raiz.getChildren("facto");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); // Obtem escritor i da Lista 
            if (esc.getAttributeValue("nome").contains(countrie)) {
                Element ocup = (Element) esc.getChild("paises_vizinhos");
                List lista_oc = ocup.getChildren("pais_vizinho");
                for (int j = 0; j < lista_oc.size(); j++) {
                    Element o = (Element) lista_oc.get(j);
                    if (o.getText().equals(antigoPais)) {
                        o.setText(novoPais); // Altera o texto da ocupação
                        System.out.println("Pais alterado com sucesso!");
                        found = true;
                        break; // Sai do loop assim que a alteração for feita
                    }
                }
            }
            if (found) {
                break; // Sai do loop principal assim que a alteração for feita
            }
        }
        if (!found) {
            System.out.println("Pais " + countrie + " ou pais vizinho " + antigoPais + " não foi encontrado");
            return null;
        }
        return doc;
    }

    // Novo método para adicionar idioma
    public static Document adicionaIdioma(String countrie, String novoIdioma, Document doc) throws SaxonApiException {
        Element raiz;
    if (doc == null) {
        System.out.println("Ficheiro não existe - não dá para adicionar informação");
        return null;
    } else {
        raiz = doc.getRootElement();
    }

    List<Element> todos = raiz.getChildren("facto");
    boolean found = false;
    for (Element pais : todos) {
        if (pais.getAttributeValue("nome").equalsIgnoreCase(countrie)) {
            Element idiomas = pais.getChild("idiomas_oficiais");
            if (idiomas == null) {
                idiomas = new Element("idioma");
                pais.addContent(idiomas);
            }

            List<Element> listaIdiomas = idiomas.getChildren("idioma");
            boolean idiomaExiste = false;
            for (Element idioma : listaIdiomas) {
                if (idioma.getText().equalsIgnoreCase(novoIdioma)) {
                    idiomaExiste = true;
                    break;
                }
            }

            if (!idiomaExiste) {
                Element novoElemIdioma = new Element("idioma");
                novoElemIdioma.setText(novoIdioma);
                idiomas.addContent(novoElemIdioma);
                System.out.println("Idioma adicionado com sucesso!");
            } else {
                System.out.println("O idioma já existe para este país.");
            }
            found = true;
            break; // Parar a iteração, já que encontramos o país
        }
    }

    if (!found) {
        System.out.println("País " + countrie + " não foi encontrado");
        return null;
    }

    return doc;

    }
}
