package com.example.salimsp.testyouriq;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizAct extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        backPressAlertBox();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().hide();

        if(ScoreBoard.username.trim().isEmpty() ) {
            // Method.addFrag(getFragmentManager(), R.id.quizContainer, new FragStartQuiz());
        }
        else{

            finish();
        }

     }

    public void backPressAlertBox(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(QuizAct.this);

        alert.setTitle("Exit");

        alert.setMessage("Are you want to exit.");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                if (getIntent().getBooleanExtra("EXIT", true)) {
                    finish();
                }

            }

        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alert.setCancelable(true);

            }
        });

        alert.create().show();
    }

}
