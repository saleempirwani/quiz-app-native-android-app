package com.example.salimsp.testyouriq;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.util.Locale;

public class FragQuiz extends Fragment {

TextView tvTimer, tvQt, tvQno;
RadioGroup rdgpOpts;
Button btnNext, btnPre;
RadioButton rdOp1, rdOp2, rdOp3;

int i = 0;
String ans[] = new String[Database.data.length];
View v;

private  static long MILLIS = 120000;
private long count = MILLIS;
CountDownTimer cdt;
boolean timeRunning;




    public FragQuiz() {

 }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       v = inflater.inflate(R.layout.fragment_frag_quiz, container, false);


            tvTimer = v.findViewById(R.id.tvTimer);
            tvQno= v.findViewById(R.id.tvQno);
            tvQt = v.findViewById(R.id.tvQt);
            rdgpOpts = v.findViewById(R.id.rdgpOpts);
            btnNext = v.findViewById(R.id.btnNext);
            rdOp1 = v.findViewById(R.id.rdOp1);
            rdOp2 = v.findViewById(R.id.rdOp2);
            rdOp3 = v.findViewById(R.id.rdOp3);

            startTimer();

        opt();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opt1();
            }

            });

           updateText();
        return v;

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

    public void opt() {

        tvQno.setText("Question "+(i+1)+"/"+(Database.data.length));
        tvQt.setText("Q" + (i + 1) + ". " + Database.data[i][0]);
        rdOp1.setText(Database.data[i][1]);
        rdOp2.setText(Database.data[i][2]);
        rdOp3.setText(Database.data[i][3]);

       rdgpOpts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

               int rdid = rdgpOpts.getCheckedRadioButtonId();
                RadioButton rd = v.findViewById(rdid);
                ans[i] = rd.getText().toString();
           }

        });

        if (i == Database.data.length - 1) {

            btnNext.setText("Finish");

        }
    }

    public void opt1(){

        if(rdgpOpts.getCheckedRadioButtonId()>0) {


            if (i < Database.data.length - 1) {

                i++;

                tvQno.setText("Question "+(i+1)+"/"+(Database.data.length));
                tvQt.setText("Q" + (i + 1) + ". " + Database.data[i][0]);
                rdOp1.setText(Database.data[i][1]);
                rdOp2.setText(Database.data[i][2]);
                rdOp3.setText(Database.data[i][3]);

                int rid = rdgpOpts.getCheckedRadioButtonId();
                RadioButton rd = v.findViewById(rid);
                ans[i] = rd.getText().toString();

                if (i == Database.data.length - 1) {

                    btnNext.setText("Finish");
                }
            }

            else

            {
                pauseTimer();
                checkAns();
                new Database(getActivity()).insertScore(ScoreBoard.username, ScoreBoard.subject, ScoreBoard.score, ScoreBoard.time);
                scoreAlertBox();


            }
        }

        else{

          //  mistakeAlertBox();
        }

        }


    public void scoreAlertBox(){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Score");

        alert.setMessage(

                " Username: "+ScoreBoard.username+
              " \n\nSubject: "+ScoreBoard.subject+
              " \n\nScore: "+ScoreBoard.score+
              " \n\nTime: "+ScoreBoard.time
        );

        alert.setPositiveButton("Show Answer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // Method.addFrag(getFragmentManager(), R.id.quizContainer, new FragQuizAnswers());

            }

        }).setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });

        alert.create().show();

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
    private void resetTimer(){

        count = MILLIS;
        updateText();
    }

    private  void updateText(){

        int min = (int) (count / 1000) / 1000;
        int sec = (int) (count / 1000) % 60;

        String format = String.format(Locale.getDefault(),"%02d : %02d" ,min ,sec);

        tvTimer.setText(format);

        ScoreBoard.time = format;
    }

    @Override
    public void onResume() {
        super.onResume();

        startTimer();
    }


    @Override
    public void onPause() {
        super.onPause();

        pauseTimer();
    }

}

