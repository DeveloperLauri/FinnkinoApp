package com.oliot5.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    String username = "";

    Fragment_Main fragmain = new Fragment_Main();
    Fragment_history fraghistory = new Fragment_history();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username = getIntent().getExtras().get("key").toString();
        View.OnClickListener listener = new View.OnClickListener() {
            Fragment fragment;

            @Override // Button activity
            public void onClick(View view) {
                if ( view == findViewById(R.id.MovieManagerButton)){
                    sendUsernameToFragment();
                    System.out.println(username);
                    fragment = fragmain;

                } else {
                    sendUsernameToFragment();
                    fragment = fraghistory;
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
    public void sendUsernameToFragment () {
        Bundle bundle = new Bundle();
        bundle.putString("key",username);

        fragmain.setArguments(bundle);
        fraghistory.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        String lie = "hukkakuski";
        Intent intent = new Intent();
        intent.putExtra("key", lie);
        setResult(RESULT_OK, intent);
        finish();
    }

}