package com.example.salimsp.testyouriq;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.Toast;

public class ScoreBoard extends AppCompatActivity {

    public static String username = "";
    public static String subject = "";
    public static int score = 0;
    public static String time = "";
    int i;
    TextView tvNoScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        getSupportActionBar().hide();

       ImageButton imgbtnResetRecord = findViewById(R.id.imgbtnResetRecord);

       tvNoScore = findViewById(R.id.tvNoScore);


        imgbtnResetRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetRecordAlertBox();
            }
        });


       init();

    }

    public void resetRecordAlertBox() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(ScoreBoard.this);

        alert.setTitle("Reset Record");

        alert.setMessage("Are you want to reset record.");

        alert.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new Database(ScoreBoard.this).deleteScore();

                init();
               finish();
               startActivity(getIntent());

            }

        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alert.setCancelable(true);

            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));
        negButton.setTextColor(getResources().getColor(R.color.BLACK));
    }

    public void init() {

        new Database(ScoreBoard.this).getScore();

        int len = Database.scoreData.length;

        if (len != 0) {

            tvNoScore.setText("");

        }

        RelativeLayout relLayout = findViewById(R.id.relLayout);
        int top = 10;

        for (i = 0; i < len; i++) {

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lp.setMargins(60, top, 0, 0);
            TextView tvUsername = new TextView(this);
            tvUsername.setLayoutParams(lp);
            tvUsername.setText(Database.scoreData[i][0]);
            tvUsername.setTextColor(getResources().getColor(R.color.BLACK));
            relLayout.addView(tvUsername);

            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp1.setMargins(270, top, 0, 0);
            TextView tvSubject = new TextView(this);
            tvSubject.setLayoutParams(lp1);
            tvSubject.setText(Database.scoreData[i][1]);
            tvSubject.setTextColor(getResources().getColor(R.color.BLACK));
            relLayout.addView(tvSubject);

            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp2.setMargins(475, top, 0, 0);
            TextView tvScore = new TextView(this);
            tvScore.setLayoutParams(lp2);
            tvScore.setText("0" + Database.scoreData[i][2]);
            tvScore.setTextColor(getResources().getColor(R.color.BLACK));
            relLayout.addView(tvScore);

            RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp3.setMargins(610, top, 0, 0);
            TextView tvTime = new TextView(this);
            tvTime.setLayoutParams(lp3);
            tvTime.setText(Database.scoreData[i][3]);
            tvTime.setTextColor(getResources().getColor(R.color.BLACK));
            relLayout.addView(tvTime);

            top += 40;
        }
    }
}