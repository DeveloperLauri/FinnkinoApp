package com.oliot5.finnkino;


import java.util.ArrayList;
import java.util.List;

public class Kirjasto {
    private String ID;
    private String theatreName;
    ArrayList<Tiedot> arrayLista = new ArrayList<>();
    List<String> movieLista = new ArrayList<>();
    List<String> idLista = new ArrayList<>();


    public void AddToList (String id, String tName) {
        ID = id;
        theatreName = tName;
        arrayLista.add(new Tiedot(ID, theatreName));
    }
    public List getMovieList() {
        for (int i = 0; i < arrayLista.size(); i++) {
            movieLista.add(arrayLista.get(i).getTheatreName());

            //for (int j = 0; j < arrayLista.get(i).getTheatreName().length(); j++) {
               //arrayLista.get(i).getTheatreName().getCh
            //}
        }
        return movieLista;
    }

    public List getIdList() {
        for (int i = 0; i < arrayLista.size(); i++) {
            idLista.add(arrayLista.get(i).getID());
        }
        return idLista;
    }
}
