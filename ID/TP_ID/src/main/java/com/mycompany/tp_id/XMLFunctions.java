package com.mycompany.tp_id;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;


public class XMLFunctions {

    public static Document adicionaAutor(Autor autor, Document doc) {
        Element raiz;

        if (doc == null) {
            raiz = new Element("escritores");
            doc = new Document(raiz);
        } else {
            try {
                raiz = doc.getRootElement();
                String xp = "//escritor[@nome ='" + autor.getNome() + "']";
                XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
                
                if (res != null && res.size() > 0) {
                    System.out.println("O escritor " + autor.getNome() + " já existe!");
                    return null;
                } else {



                    Element pai = new Element("autor");

                    Attribute nome = new Attribute("nome", autor.getNome());
                    pai.setAttribute(nome);

                    nome = new Attribute("id", autor.getId_autor());
                    pai.setAttribute(nome);


                    Element dados = new Element("Dados");

                    Element filho = new Element("nacionalidade").addContent(autor.getNacionalidade());
                    dados.addContent(filho);

                    filho = new Element("nascimento").addContent(autor.getDataNascimento());
                    dados.addContent(filho);

                    filho = new Element("morte").addContent(autor.getDataMorte());
                    dados.addContent(filho);

                    filho = new Element("foto").addContent(autor.getLinkFoto());
                    dados.addContent(filho);

                    filho = new Element("genero").addContent(autor.getGeneroLiterario());
                    dados.addContent(filho);

                    filho = new Element("ocupacao").addContent(autor.getOcupaçoes());
                    dados.addContent(filho);

                    pai.addContent(dados);



                    raiz.addContent(pai);
                }
            } catch (SaxonApiException ex) {
                Logger.getLogger(XMLFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return doc;

    }

    public static Document adicionaObra(Obra obra, Document doc) {
        Element raiz;

        if (doc == null) {
            raiz = new Element("obras");
            doc = new Document(raiz);
        } else {
            try {
                raiz = doc.getRootElement();
                String xp = "//obras[@nome ='" + obra.getTitulo() + "']";
                XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");

                if (res != null && res.size() > 0) {
                    System.out.println("A obra " + obra.getTitulo() + " já existe!");
                    return null;
                } else {



                    Element pai = new Element("obra");

                    Attribute nome = new Attribute("isbn", obra.getISBN());
                    pai.setAttribute(nome);

                    nome = new Attribute("id", obra.getIdAutor());
                    pai.setAttribute(nome);



                    Element dados = new Element("Dados");

                    Element filho = new Element("nomeautor").addContent(obra.getNomeAutor());
                    dados.addContent(filho);

                    filho = new Element("titulo").addContent( obra.getTitulo());
                    dados.addContent(filho);

                    filho = new Element("editora").addContent(obra.getEditora());
                    dados.addContent(filho);

                    filho = new Element("preco").addContent(obra.getPreco());
                    dados.addContent(filho);

                    filho = new Element("linkcapa").addContent(obra.getLinkCapa());
                    dados.addContent(filho);






                    pai.addContent(dados);



                    raiz.addContent(pai);
                }
            } catch (SaxonApiException ex) {
                Logger.getLogger(XMLFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return doc;

    }

    ;
        public static Document eliminaAutor(String procura, Document doc) {
        Element raiz;

        if (doc == null) {
            System.out.println("Ficheiro XML não existe!\n");
        }
        raiz = doc.getRootElement();
        List todasAutores = raiz.getChildren("autor");
        boolean found = false;

        for (int i = 0; i < todasAutores.size(); ++i) {
            Element autor = (Element) todasAutores.get(i);
            if (autor.getAttributeValue("nome").equals(procura)) {
                autor.getParent().removeContent(autor);
                System.out.println("Autor eliminado com sucesso!");
                found = true;
            }
        }
        if (!found) {
            System.out.println("O Autor " + procura + " nao foi encontrado");
            return null;
        }
        return doc;

    }

    public static Document eliminaObra(String procura, Document doc) {
        Element raiz;

        if (doc == null) {
            System.out.println("Ficheiro XML não existe!\n");
        }
        raiz = doc.getRootElement();
        List todasObras = raiz.getChildren("obra");
        boolean found = false;

        for (int i = 0; i < todasObras.size(); ++i) {
            Element obra = (Element) todasObras.get(i);
            if (obra.getAttributeValue("isbn").equals(procura)) {
                obra.getParent().removeContent(obra);
                System.out.println("Obra eliminada com sucesso!");
                found = true;
            }
        }
        if (!found) {
            System.out.println("A obra " + procura + " nao foi encontrado");
            return null;
        }
        return doc;

    }
    public static Document alteraNacionalidade(String autor, String novoNacionalidade, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosAutores = raiz.getChildren("autor");
            boolean found = false;

            for (i = 0; i < todosAutores.size(); i++) {
                Element auto = (Element) todosAutores.get(i);
                if (auto.getAttributeValue("nome").equals(autor)) {
                    auto.getChild("Dados").getChild("nacionalidade").getText();
                    auto.getChild("Dados").getChild("nacionalidade").setText(novoNacionalidade);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("O autor " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraNascimento(String autor, String novoNascimento, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosAutores = raiz.getChildren("autor");
            boolean found = false;

            for (i = 0; i < todosAutores.size(); i++) {
                Element auto = (Element) todosAutores.get(i);
                if (auto.getAttributeValue("nome").equals(autor)) {
                    auto.getChild("Dados").getChild("nascimento").getText();
                    auto.getChild("Dados").getChild("nascimento").setText(novoNascimento);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("O autor " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraMorte(String autor, String novoMorte, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosAutores = raiz.getChildren("autor");
            boolean found = false;

            for (i = 0; i < todosAutores.size(); i++) {
                Element auto = (Element) todosAutores.get(i);
                if (auto.getAttributeValue("nome").equals(autor)) {
                    auto.getChild("Dados").getChild("morte").getText();
                    auto.getChild("Dados").getChild("morte").setText(novoMorte);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("O autor " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraGenero(String autor, String novoGenero, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosAutores = raiz.getChildren("autor");
            boolean found = false;

            for (i = 0; i < todosAutores.size(); i++) {
                Element auto = (Element) todosAutores.get(i);
                if (auto.getAttributeValue("nome").equals(autor)) {
                    auto.getChild("Dados").getChild("genero").getText();
                    auto.getChild("Dados").getChild("genero").setText(novoGenero);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("O autor " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }


    public static Document alteraOcupacao(String autor, String novoOcupacao, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosAutores = raiz.getChildren("autor");
            boolean found = false;

            for (i = 0; i < todosAutores.size(); i++) {
                Element auto = (Element) todosAutores.get(i);
                if (auto.getAttributeValue("nome").equals(autor)) {
                    auto.getChild("Dados").getChild("ocupacao").getText();
                    auto.getChild("Dados").getChild("ocupacao").setText(novoOcupacao);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("O autor " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraNomeAutor(String autor, String novoNomeAutor, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosObras = raiz.getChildren("obra");
            boolean found = false;

            for (i = 0; i < todosObras.size(); i++) {
                Element obr = (Element) todosObras.get(i);
                if (obr.getAttributeValue("isbn").equals(autor)) {
                    obr.getChild("Dados").getChild("nomeautor").getText();
                    obr.getChild("Dados").getChild("nomeautor").setText(novoNomeAutor);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("A obra " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraTitulo(String autor, String novoTitulo, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosObras = raiz.getChildren("obra");
            boolean found = false;

            for (i = 0; i < todosObras.size(); i++) {
                Element obr = (Element) todosObras.get(i);
                if (obr.getAttributeValue("isbn").equals(autor)) {
                    obr.getChild("Dados").getChild("titulo").getText();
                    obr.getChild("Dados").getChild("titulo").setText(novoTitulo);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("A obra " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraEditora(String autor, String novoEditora, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosObras = raiz.getChildren("obra");
            boolean found = false;

            for (i = 0; i < todosObras.size(); i++) {
                Element obr = (Element) todosObras.get(i);
                if (obr.getAttributeValue("isbn").equals(autor)) {
                    obr.getChild("Dados").getChild("editora").getText();
                    obr.getChild("Dados").getChild("editora").setText(novoEditora);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("A obra " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }

    public static Document alteraPreco(String autor, String novoPreco, Document doc) {
        Element raiz;
        int i;
        if (doc == null) {
            System.out.println("Ficheiro não existe");
            return null;
        } else {
            raiz = doc.getRootElement();

            List todosObras = raiz.getChildren("obra");
            boolean found = false;

            for (i = 0; i < todosObras.size(); i++) {
                Element obr = (Element) todosObras.get(i);
                if (obr.getAttributeValue("isbn").equals(autor)) {
                    obr.getChild("Dados").getChild("preco").getText();
                    obr.getChild("Dados").getChild("preco").setText(novoPreco);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("A obra " + autor + " não foi encontrado!");
                return null;
            }
        }
        return doc;
    }
}
