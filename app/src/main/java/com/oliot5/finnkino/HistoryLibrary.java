package com.oliot5.finnkino;

import java.util.ArrayList;
import java.util.List;

public class HistoryLibrary {
    private String movieName;
    private String stars;
    ArrayList<HistoryInfo> arrayLista = new ArrayList<>();
    List<String> movieList = new ArrayList<>();
    List<String> starList = new ArrayList<>();


    public void AddToList (String mName, String star) {
        movieName = mName;
        stars = star;
        arrayLista.add(new HistoryInfo(movieName, stars));
    }
    public List getMovieList() {
        for (int i = 0; i < arrayLista.size(); i++) {
            movieList.add(arrayLista.get(i).getMovieName());

        }
        return movieList;
    }

    public List getIdList() {
        for (int i = 0; i < arrayLista.size(); i++) {
            starList.add(arrayLista.get(i).getStars());
        }
        return starList;
    }
}
