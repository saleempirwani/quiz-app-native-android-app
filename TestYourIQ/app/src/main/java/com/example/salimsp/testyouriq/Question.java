package com.example.salimsp.testyouriq;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class Question extends AppCompatActivity {

    TextView tvTimer, tvQt, tvQno;
    RadioGroup rdgpOpts;
    Button btnNext;
    RadioButton rdOp1, rdOp2, rdOp3;
    Button btnStart;

    int i = 0;
    String ans[] = new String[Database.data.length];

    private  static long MILLIS = 120000;
    private long count = MILLIS;
    CountDownTimer cdt;
    boolean timeRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);

        getSupportActionBar().hide();

        tvTimer = findViewById(R.id.tvTimer);
        tvQno= findViewById(R.id.tvQno);
        tvQt = findViewById(R.id.tvQt);
        rdgpOpts = findViewById(R.id.rdgpOpts);
        btnNext = findViewById(R.id.btnNext);
        rdOp1 = findViewById(R.id.rdOp1);
        rdOp2 = findViewById(R.id.rdOp2);
        rdOp3 = findViewById(R.id.rdOp3);

        setQuestion();
        rdgpOpts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(i < Database.data.length)
                    getAnswer();

                Log.i("Answer"+i,ans[i]+"");

            }

        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isradioChecked()){

                    radioSetUncheck();

                    i++;

                    if (i < Database.data.length-1 ){ // i < 9

                        setQuestion();
                        getAnswer();
                    }
                    else if(i == Database.data.length-1){  // i == 9

                        btnNext.setText("Finish");
                        setQuestion();
                        getAnswer();
                    }
                    else{

                        result();

                    }
                        }

                else{

                    mistakeAlert();
                }
            }

        });

        updateText();
    }

    public void getAnswer(){

        if(rdOp1.isChecked())
            ans[i] = rdOp1.getText().toString();

        else if (rdOp2.isChecked())
            ans[i] = rdOp2.getText().toString();

        else if (rdOp3.isChecked())
            ans[i] = rdOp3.getText().toString();
    }

    public void checkAns(){

        for (int j = 0; j < ans.length; j++) {

            if (ans[j].equals(Database.data[j][4])) {

                ScoreBoard.score++;
            }

            Log.i("ans",ans[j]+"");

        }

        Log.i("ans[j]",""+ans.length);
    }

    public boolean isradioChecked(){

        if(rdOp1.isChecked())
            return true;
        else if (rdOp2.isChecked())
            return true;
        else if (rdOp3.isChecked())
            return  true;

        return false;
    }

    public void setQuestion(){

            tvQno.setText("Question "+(i+1)+"/"+(Database.data.length));
            tvQt.setText("Q" + (i + 1) + ". " + Database.data[i][0]);
            rdOp1.setText(Database.data[i][1]);
            rdOp2.setText(Database.data[i][2]);
            rdOp3.setText(Database.data[i][3]);
        }

    public void result(){
        pauseTimer();
        checkAns();
        new Database(Question.this).insertScore(ScoreBoard.username, ScoreBoard.subject, ScoreBoard.score, ScoreBoard.time);
        scoreAlertBox();
    }

public void radioSetUncheck(){

    rdOp1.setChecked(false);
    rdOp2.setChecked(false);
    rdOp3.setChecked(false);
}
    public void scoreAlertBox(){

        pauseTimer();

        AlertDialog.Builder alert = new AlertDialog.Builder(Question.this);

        alert.setTitle("Score");

        alert.setMessage(

                " Username: "+ScoreBoard.username+
                        " \n\nSubject: "+ScoreBoard.subject+
                        " \n\nScore: "+ScoreBoard.score+
                        " \n\nTime: "+ScoreBoard.time
        );

        alert.setCancelable(false).setPositiveButton("Show Answer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Question.this, Answer.class));
                finish();

            }

        }).setNeutralButton("Score board", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Question.this, ScoreBoard.class));
                finish();
            }

        }).setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button neuButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));
        negButton.setTextColor(getResources().getColor(R.color.BLACK));
        neuButton.setTextColor(getResources().getColor(R.color.BLACK));

    }

    private void startTimer(){

        cdt = new CountDownTimer(count,1000) {
            @Override
            public void onTick(long l) {

                count = l;
                updateText();
            }

            @Override
            public void onFinish() {

                timeRunning = false;

            }
        }.start();

        timeRunning = true;
    }

    private void pauseTimer(){

        cdt.cancel();
        timeRunning = false;
    }

    private  void updateText(){

        int min = (int) (count / 1000) / 60;
        int sec = (int) (count / 1000) % 60;


        String format = String.format(Locale.getDefault(),"%02d : %02d" ,min ,sec);

        tvTimer.setText(format);

        ScoreBoard.time = format;

        if(ScoreBoard.time.equals("00 : 01")){

            tvTimer.setText("00 : 00");

            timeUpAlert();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        pauseTimer();
    }

    @Override
    public void onBackPressed() {

        pauseTimer();

        final AlertDialog.Builder alert = new AlertDialog.Builder(Question.this);

        alert.setTitle("Leave Test");

        alert.setMessage("Are you want to exit");

        alert.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Question.super.onBackPressed();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startTimer();
                alert.setCancelable(true);

            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));
        negativeButton.setTextColor(getResources().getColor(R.color.BLACK));
        neutralButton.setTextColor(getResources().getColor(R.color.BLACK));
    }


    public void mistakeAlert(){

        pauseTimer();

        final AlertDialog.Builder alert = new AlertDialog.Builder(Question.this);

        alert.setTitle("Mistake");

        alert.setMessage("Please select any option.");

        alert.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startTimer();
                alert.setCancelable(true);


            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));
    }

    public void timeUpAlert(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(Question.this);

        alert.setTitle("Game Over");

        alert.setMessage("You did not reach the goal.");

        alert.setCancelable(false).setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));
    }

}


