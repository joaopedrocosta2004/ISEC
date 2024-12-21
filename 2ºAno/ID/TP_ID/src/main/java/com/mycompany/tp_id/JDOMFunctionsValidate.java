package com.mycompany.tp_id;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;


public class JDOMFunctionsValidate {
    //Executa validação do documento XML usando  DTD 

    public static Document validarDTD(String caminhoFicheiro) throws IOException, JDOMException {
        try {
            SAXBuilder builder = new SAXBuilder(true);  // true ativa a validação
            Document doc = (Document) builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " é válido (DTD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e não é válido (DTD)");
            Logger.getLogger(JDOMFunctionsValidate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " nao foi encontrado");
            Logger.getLogger(JDOMFunctionsValidate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Executa validação do documento XML usando XSD
    public static Document validarXSD(String caminhoFicheiro) {
        try {
            SAXBuilder builder = new SAXBuilder(true); // true ativa a validação

            // esta linha ativa a validação com XSD
            builder.setFeature("http://apache.org/xml/features/validation/schema", true);

            Document doc = (Document) builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " é válido (XSD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e não é válido (XSD)");
            Logger.getLogger(JDOMFunctionsValidate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " nao foi encontrado");
            Logger.getLogger(JDOMFunctionsValidate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int validarDocumentoDTD(String xmlFile, String DTDFile) throws IOException, JDOMException {
        Document doc = (Document) XMLJDomFunctions.lerDocumentoXML(xmlFile);
        File f = new File(DTDFile);
        if (doc != null && f.exists()) { //DTD e XML existem
            Element raiz = doc.getRootElement();
            //Atribuir DTD ao ficheiro XML
            DocType dtd = new DocType(raiz.getName(), DTDFile);
            doc.setDocType(dtd);

            //Gravar o XML com as alterações em disco
            XMLJDomFunctions.escreverDocumentoParaFicheiro((org.jdom2.Document) doc, xmlFile);

            //CHAMAR A FUNÇÃO DE VALIDAÇÃO por DTD
            Document docDTD = JDOMFunctionsValidate.validarDTD(xmlFile);
            if (docDTD == null) {
                System.out.println("INVALIDO por DTD");
                return -1;
            } else {
                System.out.println("VALIDO por DTD");
                return 1;
            }
        }
        return 0;
    }

    public static int validarDocumentoXSD(String xmlFile, String XSDFile) {
        Document doc = (Document) XMLJDomFunctions.lerDocumentoXML(xmlFile);
        File f = new File(XSDFile);
        if (doc != null && f.exists()) {//XSD e XML existem
            Element raiz = doc.getRootElement();
            //Atribuir XSD ao ficheiro XML
            Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            raiz.addNamespaceDeclaration(XSI);
            raiz.setAttribute(new Attribute("noNamespaceSchemaLocation", XSDFile,
                    Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
            //Gravar o XML com as alterações em disco
            XMLJDomFunctions.escreverDocumentoParaFicheiro((org.jdom2.Document) doc, xmlFile);

            //CHAMAR A FUNÇÃO DE VALIDAÇÃO por XSD
            Document docXSD = JDOMFunctionsValidate.validarXSD(xmlFile);
            if (docXSD == null) {
                System.out.println("INVALIDO por XSD");
                return -1;
            } else {
                System.out.println("VALIDO por XSD");
                return 1;
            }
        }
        return 0;
    }

}
