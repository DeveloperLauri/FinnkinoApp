package com.oliot5.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        View.OnClickListener listener = new View.OnClickListener() {
            Fragment fragment;

            @Override
            public void onClick(View view) {
                if ( view == findViewById(R.id.login_button)){
                    System.out.println("Moikka");
                    fragment = new Fragment_Main();
                } else {
                    System.out.println("Help me!");
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentwindow,fragment);
                transaction.commit();
            }
        };

        Button btn = findViewById(R.id.login_button);
        btn.setOnClickListener(listener);

    }
}