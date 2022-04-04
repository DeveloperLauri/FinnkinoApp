package com.oliot5.finnkino;


import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Paaluokka {
    String[] theatre = new String[0];
    String[] id = new String[0];
    String startTime, theatreName, movieName;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> readXML2(String id, String date, String time1, String time2, String movie) {
        String bwoah;
        int j = 0;
        LocalTime timeA, timeB, timeMovie;
        ArrayList<String> lista2 = new ArrayList<>();

        if(time1.isEmpty()) {
            time1 = "00:00";
        }
        if(time2.isEmpty()) {
            time2 = "23:59";
        }

        String[] arrOfStr2 = time1.split(":");
        String[] arrOfStr3 = time2.split(":");
        timeA = LocalTime.of(Integer.parseInt(arrOfStr2[0]), Integer.parseInt(arrOfStr2[1]));
        timeB = LocalTime.of(Integer.parseInt(arrOfStr3[0]), Integer.parseInt(arrOfStr3[1]));
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

                    bwoah = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    String[] arrOfStr = bwoah.split("T");
                    bwoah = arrOfStr[1];

                    //picking up starting time
                    startTime = bwoah;

                    arrOfStr = bwoah.split(":");

                    int hours1 = Integer.parseInt(arrOfStr[0]);
                    int minutes1 = Integer.parseInt(arrOfStr[1]);
                    timeMovie = LocalTime.of(hours1,minutes1);

                    // timeA pitää olla pienempi kuin timeMovie
                    int returnVal1 = timeA.compareTo(timeMovie);
                    //timeB pitää olla suurempi kuin timeMovie
                    int returnVal2 = timeB.compareTo(timeMovie);

                    movieName = element.getElementsByTagName("Title").item(0).getTextContent();

                    // when the "Elokuvan nimi/ Movie name" field is empty – this if statement is true
                    // -> only movie name is added to lista2
                    if(returnVal1 <= 0 && returnVal2 >= 0 && movie.isEmpty()) {
                        lista2.add(movieName);
                    }

                    // adds movie name (once), location and time information for the asked movie to lista2
                    if(returnVal1 <= 0 && returnVal2 >= 0 && movieName.equals(movie)){
                        if(j == 0){
                            lista2.add(movieName);
                            j++;
                        }
                        theatreName = element.getElementsByTagName("Theatre").item(0).getTextContent();
                        lista2.add(theatreName + ": " + startTime);
                    }

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

    public void saveEntries(String entry){
        System.out.println("Ollaan saveEntries metodissa ja entry on: " + entry);

    }

}
