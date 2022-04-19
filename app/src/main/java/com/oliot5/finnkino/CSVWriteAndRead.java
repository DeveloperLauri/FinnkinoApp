package com.oliot5.finnkino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriteAndRead {

    public void writeCSVFile(String username, String password) throws IOException {
        File fileUserPass = new File("userPassCheck.csv");
        FileWriter fw = new FileWriter(fileUserPass.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        // if file doesn't exists, then create
        if(!fileUserPass.exists()) {
            fileUserPass.createNewFile();
            bw.write("username;password");
        }
        String content = username+";"+password;
        bw.append(content);
        bw.close();

    }

    public String readCSVVFile(String username, String password) {
        BufferedReader br = null;
        String whatNext = "ei jatkoon";

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("userPassCheck.csv"));
            while((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                whatNext = "jatkoon";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return whatNext;
    }
}
