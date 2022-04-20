package com.oliot5.finnkino;

public class HistoryInfo {
    private String movieName;
    private String stars;

    public HistoryInfo (String mName, String star) {
        movieName = mName;
        stars = star;
    }

    @Override
    public String toString() {
        return movieName+",    Stars: "+stars;
    }

    public String getMovieName(){ return movieName; }
    public String getStars(){ return stars; }
}
