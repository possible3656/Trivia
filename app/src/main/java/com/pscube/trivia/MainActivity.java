package com.pscube.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pscube.trivia.data.asyncanswer;
import com.pscube.trivia.data.questionBank;
import com.pscube.trivia.models.questions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new questionBank().getQuestions(new asyncanswer() {
            @Override
            public void processFinished(ArrayList<questions> questionsArrayList) {
                Log.d("happy1", "processFinished: "+questionsArrayList);
            }
        });


    }
}
