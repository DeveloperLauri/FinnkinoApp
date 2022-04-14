package com.oliot5.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        View.OnClickListener listener = new View.OnClickListener() {
            Fragment fragment;

            @Override
            public void onClick(View view) {
                if ( view == findViewById(R.id.MovieManagerButton)){
                    System.out.println("Moikka");
                    fragment = new Fragment_Main();
                } else {
                    fragment = new Fragment_history();
                    System.out.println("Help me!");
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentwindow,fragment);
                transaction.commit();
            }
        };

        Button movieManagerButton = findViewById(R.id.MovieManagerButton);
        movieManagerButton.setOnClickListener(listener);
        Button historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(listener);

    }
}