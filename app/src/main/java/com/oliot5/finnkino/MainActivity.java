package com.oliot5.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] theatre;
    String[] id;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextMovie;
    Paaluokka pLuokka = new Paaluokka();
    ListView listView;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        theatre = pLuokka.readXML();
        id = pLuokka.getIDList();

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        editTextMovie = (EditText) findViewById(R.id.editTextMovie);
        listView = (ListView) findViewById(R.id.listView);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, theatre);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),theatre[i] , Toast.LENGTH_LONG).show();

        ArrayList<String> arrayList = pLuokka.readXML2(id[i], "2022-03-25T00:00:00");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}