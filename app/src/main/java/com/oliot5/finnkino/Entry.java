package com.oliot5.finnkino;

import java.util.Date;

public class Entry {
    private Date date;
    Stars st;
    int amountOfStars;

    Entry(){

    }

    Entry(int stars){
        st = new Stars(stars);
    }

    public int getAmountOfStars(){
        amountOfStars = st.getAmountOfStars();
        return amountOfStars;
    }
}
