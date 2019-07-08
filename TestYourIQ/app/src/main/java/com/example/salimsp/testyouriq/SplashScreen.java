package com.example.salimsp.testyouriq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    int delay = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();



        Thread td = new Thread(){

            public void run(){

                try{

                    sleep(delay);
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }
                 catch(Exception e){

                    Toast.makeText(SplashScreen.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("Error",e.getMessage());
                 }
            }

        }; td.start();
    }

}
