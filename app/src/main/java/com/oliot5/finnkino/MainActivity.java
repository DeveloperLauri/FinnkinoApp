package com.oliot5.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
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
                if ( view == findViewById(R.id.buttonToLogIn)){
                    System.out.println("Moikka");
                    fragment = new Fragment_login();
                } else if ( view == findViewById(R.id.buttonToRegistrate)) {
                    fragment = new Fragment_register();
                }
                else {
                    System.out.println("Help me!");
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentwindow,fragment);
                transaction.commit();
            }
        };

        Button buttonToLogin = findViewById(R.id.buttonToLogIn);
        buttonToLogin.setOnClickListener(listener);
        Button buttonToRegister = findViewById(R.id.buttonToRegistrate);
        buttonToRegister.setOnClickListener(listener);

    }

    public void logIn(View v) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}