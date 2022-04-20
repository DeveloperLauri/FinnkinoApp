package com.oliot5.finnkino;

import java.util.ArrayList;
import java.util.List;

public class HistoryLibrary {
    private String movieName;
    private String stars;
    ArrayList<HistoryInfo> arrayList = new ArrayList<>();
    List<String> movieList = new ArrayList<>();
    List<String> starList = new ArrayList<>();


    public void AddToList (String mName, String star) {
        movieName = mName;
        stars = star;
        arrayList.add(0, new HistoryInfo(movieName, stars));
    }

    public ArrayList<HistoryInfo> getList() {
        return arrayList;
    }

    public List getMovieList() {
        for (int i = 0; i < arrayList.size(); i++) {
            movieList.add(arrayList.get(i).getMovieName());

        }
        return movieList;
    }

    public List getIdList() {
        for (int i = 0; i < arrayList.size(); i++) {
            starList.add(arrayList.get(i).getStars());
        }
        return starList;
    }
}
