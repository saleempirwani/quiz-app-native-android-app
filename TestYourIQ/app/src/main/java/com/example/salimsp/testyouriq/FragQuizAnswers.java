package com.example.salimsp.testyouriq;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragQuizAnswers extends Fragment {

    TextView tvCrtAns, tvWrgAns, tvShowQtAns, tvScore;
    Button btnDone;
    String s = "";

    public FragQuizAnswers() {
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_quiz_answers, container, false);

        tvShowQtAns = v.findViewById(R.id.tvShowQtAns);
        btnDone = v.findViewById(R.id.btnDone);

        for(int i=0; i<Database.data.length; i++) {

            s = s + "\nQ"+(i+1)+". "+Database.data[i][0]+ "\n\nA"+(i+1)+". "+Database.data[i][4]+ "\n";

        }

        tvShowQtAns.setText(s);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });


        return  v;
    }

}
