package com.example.salimsp.testyouriq;


import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FragStartQuiz extends Fragment {
    EditText edtUsername;
Button btnStartQuiz;

    public FragStartQuiz() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v =  inflater.inflate(R.layout.fragment_frag_start_quiz, container, false);

        edtUsername = v.findViewById(R.id.edtUsername);
        btnStartQuiz = v.findViewById(R.id.btnStartQuiz);

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {

            public  void onClick(View view){

                 ScoreBoard.username = edtUsername.getText().toString();

                    if (!ScoreBoard.username.equals("")) {

                       // Method.addFrag(getFragmentManager(), R.id.quizContainer, new FragQuiz());

                    } else {

                        //Method.msgAlert(getActivity(), "Please enter username", "Message");
                    }


            }
        });

        return v;}


}
