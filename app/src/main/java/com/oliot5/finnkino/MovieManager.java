package com.oliot5.finnkino;


import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieManager {
    String[] theatre = new String[0];
    String[] id = new String[0];
    String startTime, theatreName, movieName;
    List<Information> list = new ArrayList<>();
    List<Information> idList = new ArrayList<>();
    Library library = new Library();
    private Entry entry;

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
                    library.AddToList(element.getElementsByTagName("ID").item(0).getTextContent(), element.getElementsByTagName("Name").item(0).getTextContent());
                }
            }
            list = library.getMovieList();
            theatre = list.toArray(new String[list.size()]);
            idList = library.getIdList();
            id = idList.toArray(new String[list.size()]);



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

                    // timeA must be smaller than timeMovie
                    int returnVal1 = timeA.compareTo(timeMovie);
                    //timeB must be grater than timeMovie
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

    public void saveEntries(String movie, int stars, String fileName){
//        System.out.println("Ollaan saveEntries metodissa ja entry on: " + movie + " ja tähdet: " + stars);
//        entry = new Entry(stars, movie);
//        System.out.println("Testaan tähtien tulostamista "+entry.getAmountOfStars());

//        this row will be deleted when username functionality is working
//            fileName = "data2.xml";
//            Document document = null;
//            String string = "";
//            InputStream inputStream = null;
//            try {
////                    inputStream = getContext().getAssets().open("data.xml");
//                inputStream = getContext().openFileInput(fileName);
//                int size = inputStream.available();
//                byte[] buffer = new byte[size];
//                inputStream.read(buffer);
//                string = new String(buffer);
//                inputStream.close();
//                System.out.println("Tiedoston avaaminen onnistui!");
//                System.out.println(string);
//            } catch (IOException e) {
//                e.printStackTrace();
//                string = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                        "<MovieInformation>\n" +
//                        "</MovieInformation>";
//            }
//
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder;
//
//            //Convert String to document
//            try {
//                builder = factory.newDocumentBuilder();
//                document = builder.parse(new InputSource(new StringReader(string)));
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            }
//
//            Element root = document.getDocumentElement();
////                Element newMovie = document.createElement("TestiNimi");
////
////                root.appendChild(newMovie);
//
////                Element root = document.createElement("MovieInformation");
////                document.appendChild(root);
////
//            Attr attr = document.createAttribute("Id");
//            attr.setValue("1");
//            root.setAttributeNode(attr);
////
//            Element name = document.createElement("movieName");
//            name.appendChild(document.createTextNode(movieName));
//            root.appendChild(name);
////
//            Element amountOfStars = document.createElement("stars");
//
//            amountOfStars.appendChild(document.createTextNode(Integer.toString(howManyStars)));
//            root.appendChild(amountOfStars);
//
//            //Convert document to string
//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer transformer;
//
//            String output = null;
//            try {
//                transformer = tf.newTransformer();
//                StringWriter writer = new StringWriter();
//                transformer.transform(new DOMSource(document), new StreamResult(writer));
//                output = writer.getBuffer().toString();
//            } catch (TransformerConfigurationException e) {
//                e.printStackTrace();
//            } catch (TransformerException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(output);
//
////                try {
////                    getContext().openFileInput("data.xml");
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                }
//
//            try {
//                FileOutputStream writer = getContext().openFileOutput(fileName,0);
//                writer.write(output.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

    }

}
