/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.alchemy;

import com.alchemyapi.api.AlchemyAPI;
import static com.auto.core.GlobalUtil.*;
import com.evernote.edam.type.Note;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.lang3.text.WordUtils;
import org.xml.sax.SAXException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author ethachu19
 */
public class Extractor {

    public static AlchemyAPI alchemy;
    private static Set<String> defaultTags = new LinkedHashSet<>();

    static {
        alchemy = AlchemyAPI.GetInstanceFromString("3a682637878391d479d57ec40e65ef5036968d5b");
        try {
            BufferedReader br = new BufferedReader(new FileReader("Tags.txt"));
            String str;
            while ((str = br.readLine())!=null) {
                defaultTags.add(str);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<Note> extractFromURL(List<Note> list) {
        List<Note> res = new ArrayList<>();
        for (Note n: list) {
            String link = n.getContent();
            n.unsetContent();
            try {
                String content = convertToENMLText("Gotten from " +  link ,extractTextFromURL(link));
                n.setContent(content);
                n.setTagNames(getTagsFromURL(link));
                res.add(n);
            } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException | JDOMException ex) {
                Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }
    
    public static String extractTextFromURL(String url) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, JDOMException {
        Document newDoc = convertToJDOM(alchemy.URLGetText(url));
        return newDoc.getRootElement().getChildText("text");
    }
    
    public static String extractTextXMLFromURL(String url) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        return getStringFromDocument(Extractor.alchemy.URLGetText(url));
    }
    
    public static List<Keyword> extractKeywordsFromURL(String url) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        List<Keyword> res = new ArrayList<>();
        Document doc = convertToJDOM(alchemy.URLGetRankedKeywords(url));
        Element root = doc.getRootElement();
//        print(root.toString());
        List<Element> children = ((Element)(root.getChildren("keywords").get(0))).getChildren("keyword");
        for (Element e : children) {
            String text = WordUtils.capitalizeFully(e.getChildText("text")), relevance = e.getChildText("relevance");
            if (Double.parseDouble(relevance) < 0.85)
                break;
            if (text.split(" ").length != 2)
                continue;
            res.add(new Keyword(text, e.getChildText("relevance")));
        }
        Collections.sort(res);
        return res;
    }
    
    public static List<Entity> extractEntitiesFromURL(String url) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        List<Entity> res = new ArrayList<>();
        Document doc = convertToJDOM(alchemy.URLGetRankedNamedEntities(url));
        List<Element> children = ((Element)(doc.getRootElement().getChildren("entities").get(0))).getChildren("entity");
        for (Element e : children) {
            String relevance = e.getChildText("relevance"), text = WordUtils.capitalizeFully(e.getChildText("text"));
            if (Double.parseDouble(relevance) < 0.4)
                break;
            if (!Character.isAlphabetic(text.charAt(0)))
                continue;
            res.add(new Entity(e.getChildText("type"), relevance, e.getChildText("count"), text));
        }
        return res;
    }
    
    public static List<String> getTagsFromURL(String url) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Set<String> s = new LinkedHashSet<>();
        for (Entity e : extractEntitiesFromURL(url)) {
                s.add(e.text);
        }
//        for (Keyword k : extractKeywordsFromURL(url)) {
//                tags.add(k.text);
//        }
        s.addAll(defaultTags);
        return new ArrayList<>(s);
    }
}
