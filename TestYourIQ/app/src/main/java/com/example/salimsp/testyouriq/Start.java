package com.example.salimsp.testyouriq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Start extends AppCompatActivity {

    EditText edtUsername;
    Button btnStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_start);
        edtUsername = findViewById(R.id.edtUsername);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                ScoreBoard.username = edtUsername.getText().toString();

                if (!ScoreBoard.username.equals("")) {

                    startActivity(new Intent(Start.this, Question.class));
                    finish();

                } else {

                    enterUserAlert();
                }


            }
        });
    }
    private void enterUserAlert() {


        final AlertDialog.Builder alert = new AlertDialog.Builder(Start.this);

        alert.setTitle("About");

        alert.setMessage("Please enter username");

        alert.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alert.setCancelable(true);

            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();


        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }
}
