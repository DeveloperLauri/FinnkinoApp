package com.oliot5.finnkino;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadLocale();
        setContentView(R.layout.activity_main);

        //changing the title of the actionbar
        ActionBar aBar = getSupportActionBar();
        aBar.setTitle(getResources().getString(R.string.app_name));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //functionality for changing language button
        Button changeLang = findViewById(R.id.changeLang123);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Toimiiko kielen vaihto nappi?");
                showChangeLangDialog();
            }
        });

        //functionality for registrating ang login buttons
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

    public void showChangeLangDialog() {
        //this array stores the languages to be displayed
        final String[] listingOfItems = {"English", "Spanish", "Finnish"};
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(MainActivity.this);
        lBuilder.setTitle("Choose lang...");
        lBuilder.setSingleChoiceItems(listingOfItems, -1, (dialogInterface, i) -> {
            if (i == 0) {
                //English
                setLocale("en");
                recreate();
            } else if (i == 1) {
                //Spanish
                setLocale("es");
                recreate();
            } else if (i == 2) {
                //Finnish
                setLocale("fi");
                recreate();
            }

            //dismissing the dialog when a language gets selected
            dialogInterface.dismiss();
        });

        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //saving data to the preferences that are shared
        SharedPreferences.Editor edit = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        edit.putString("My language", lang);
        edit.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang1 = preferences.getString("My lang", "");
        setLocale(lang1);
    }

    public void logIn(String un) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("key", un);
        System.out.println(un);
        startActivityForResult(intent, 1);
        //startActivity(intent);
    }

    // Method to do info change in between frag and activ
    @Override
    public void onStart() {
        super.onStart();
        try {
            String text = getIntent().getExtras().get("key").toString();
            System.out.println("#########"+text+"#####");
            logIn(text);
            /*if(text.equals("1")) {
                System.out.println("hyvä");
                text = "";
                logIn(text);
            }*/

        } catch (Exception e) {

        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String value = data.getStringExtra("key");
                System.out.println(value);
            }
        }
    }
}