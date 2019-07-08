package com.example.salimsp.testyouriq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Answer extends AppCompatActivity {

    TextView tvCrtAns, tvWrgAns, tvShowQtAns, tvScore;
    Button btnDone;
    String s = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tvShowQtAns = findViewById(R.id.tvShowQtAns);
        btnDone = findViewById(R.id.btnDone);

        getSupportActionBar().hide();

        for(int i=0; i<Database.data.length; i++) {

            s = s + "\nQ"+(i+1)+". "+Database.data[i][0]+ "\n\nA"+(i+1)+". "+Database.data[i][4]+ "\n";

        }

        tvShowQtAns.setText(s);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

}

