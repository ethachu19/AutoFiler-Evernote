/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.input.DOMBuilder;
import org.w3c.dom.Document;

/**
 *
 * @author ethachu19
 */
public class GlobalUtil {
    
//    private static SAXBuilder builder = new SAXBuilder();
    private static DOMBuilder builder = new DOMBuilder();
    
    public static void printErr(String x) {
        System.err.println(x);
    }

    public static void print(String x) {
        System.out.println(x);
    }

    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            //print(writer.toString());
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static org.jdom.Document convertToJDOM(org.w3c.dom.Document old) {
        return builder.build(old);
    }
    
    public static String convertToENMLText(String h1,String content) {
        String res =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">\n"
        + "<en-note>\n"
        + "<h4>"+ h1 + "</h4>\n"
        + "<hr/>\n"
        + "<p style=\"word-wrap: white-space:pre-wrap\">\n"
        + content + "\n</p>\n </en-note>";
        
        return res;
    }
}
