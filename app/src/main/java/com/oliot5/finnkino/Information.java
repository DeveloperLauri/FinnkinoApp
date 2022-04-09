package com.oliot5.finnkino;

public class Information {
    private String ID;
    private String theatreName;

    public Information(String id, String tName) {
        ID = id;
        theatreName = tName;
    }

    public String getID(){ return ID; }
    public String getTheatreName(){ return theatreName; }
}
