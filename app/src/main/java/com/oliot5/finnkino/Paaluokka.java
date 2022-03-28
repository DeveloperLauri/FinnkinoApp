package com.oliot5.finnkino;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Paaluokka {
    String[] theatre = new String[0];
    String[] id = new String[0];
    List<Tiedot> lista = new ArrayList<>();
    List<Tiedot> idLista = new ArrayList<>();
    Kirjasto kirjasto = new Kirjasto();

    public String[] readXML() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";

            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());


            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    kirjasto.AddToList(element.getElementsByTagName("ID").item(0).getTextContent(), element.getElementsByTagName("Name").item(0).getTextContent());
                }
            }
            lista = kirjasto.getMovieList();
            theatre = lista.toArray(new String[lista.size()]);
            idLista = kirjasto.getIdList();
            id = idLista.toArray(new String[lista.size()]);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("*****JUUH*****");
        }
        return theatre;
    }
    public String[] getIDList() {
        return id;
    }

    public ArrayList<String> readXML2(String id, String date) {
        ArrayList<String> lista2 = new ArrayList<>();
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area="+id+"&dt="+date;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName("Shows");
            NodeList nList2 = nList.item(0).getChildNodes();
            System.out.println(nList2.getLength());
            for (int i = 0; i < nList2.getLength(); i++) {
                Node node = nList2.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    lista2.add(element.getElementsByTagName("Title").item(0).getTextContent());
                    System.out.println("moi2");

                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally{
            System.out.println("******TOKA TOIMII*******");
        }
        return lista2;
    }

}
