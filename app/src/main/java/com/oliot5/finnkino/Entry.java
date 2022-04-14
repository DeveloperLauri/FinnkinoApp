package com.oliot5.finnkino;

import java.util.Date;

public class Entry {
    private Date date;
    Stars st;
    int amountOfStars;
    String movieName;

    Entry(){

    }

    Entry(int stars, String movie){
        st = new Stars(stars);
        movieName = movie;
    }

    public int getAmountOfStars(){
        amountOfStars = st.getAmountOfStars();
        return amountOfStars;
    }
    public String getMovieName(){
        return movieName;
    }
}
