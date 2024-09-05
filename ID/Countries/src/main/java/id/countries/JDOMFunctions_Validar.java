/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.countries;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 *
 * @author joaoSilveira
 */
public class JDOMFunctions_Validar {
    //Executa validação do documento XML usando  DTD 
    public static Document validarDTD(String caminhoFicheiro) throws IOException{
        try {
            SAXBuilder builder = new SAXBuilder(true);  // true ativa a validação
            Document doc = builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " é valido (DTD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e nao e valido (DTD)");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " nao foi encontrado");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ola");
        return null;
    }
    
    
    
     //Executa validação do documento XML usando XSD
    public static Document validarXSD(String caminhoFicheiro){
        try {
            SAXBuilder builder = new SAXBuilder(true); // true ativa a validação
            
            // esta linha ativa a validação com XSD
            builder.setFeature("http://apache.org/xml/features/validation/schema", true);

            Document doc = builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " e valido (XSD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e nao e valido (XSD)");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " nao foi encontrado");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
