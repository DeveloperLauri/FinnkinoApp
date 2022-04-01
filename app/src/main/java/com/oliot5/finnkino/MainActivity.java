package com.oliot5.finnkino;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] theatre;
    String[] id;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextTime2;
    EditText editTextMovie;
    Paaluokka pLuokka = new Paaluokka();
    ListView listView;
    Spinner spinner;
    String date;
    String time1 = "00:00";
    String time2 = "23:59";
    String movie = "";
    TextView textView;
    Button hae;
    int idSelecter;

    private  TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            date = editTextDate.getText().toString();
            textView.setText(date);
            time1 = editTextTime.getText().toString();
            time2 = editTextTime2.getText().toString();
            movie = editTextMovie.getText().toString();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        theatre = pLuokka.readXML();
        id = pLuokka.getIDList();
        textView = (TextView) findViewById(R.id.textView);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.addTextChangedListener(textWatcher);

        editTextTime = (EditText) findViewById(R.id.editTextTime);
        editTextTime.addTextChangedListener(textWatcher);
        editTextTime2 = (EditText) findViewById(R.id.editTextTime2);
        editTextTime2.addTextChangedListener(textWatcher);

        editTextMovie = (EditText) findViewById(R.id.editTextMovie);
        editTextMovie.addTextChangedListener(textWatcher);

        listView = (ListView) findViewById(R.id.listView);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        hae = (Button) findViewById(R.id.button);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, theatre);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        idSelecter = i;
        Toast.makeText(getApplicationContext(),theatre[i] , Toast.LENGTH_LONG).show();



        ArrayList<String> arrayList = pLuokka.readXML2(id[i], date, time1, time2, movie);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pressButton(View v) {
        ArrayList<String> arrayList = pLuokka.readXML2(id[idSelecter], date, time1, time2, movie);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}