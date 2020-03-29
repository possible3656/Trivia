package com.pscube.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pscube.trivia.data.asyncanswer;
import com.pscube.trivia.data.questionBank;
import com.pscube.trivia.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//intializing
    CardView cardView;
    TextView counterTextView;
    TextView questionTextView;
    Button   trueButton;
    Button   falseButton;
    ImageView nextImageView;
    ImageView prevImageView;
    List<questions> questionsList;
    int counter = 0;
    int x = 1;
    int y = 0;
    boolean isTrue = true;
    boolean isFalse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        cardView=findViewById(R.id.cardView);
        counterTextView=findViewById(R.id.counterTextView);
        questionTextView=findViewById(R.id.questionTextView);
        trueButton=findViewById(R.id.buttonTrue);
        falseButton=findViewById(R.id.buttonFalse);
        nextImageView=findViewById(R.id.nextImage);
        prevImageView=findViewById(R.id.prevImage);



        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
        prevImageView.setOnClickListener(this);





       questionsList=  new questionBank().getQuestions(new asyncanswer() {
            @Override
            public void processFinished(ArrayList<questions> questionsArrayList) {
                Log.d("happy1", "processFinished: "+questionsArrayList);
                questionTextView.setText(questionsArrayList.get(counter).getAnswer());
                y = questionsArrayList.size();

                counterTextView.setText(x +" out of "+y);

            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.nextImage:
                counter=counter+1;
                x= x+1;

                questionTextView.setText(questionsList.get(counter).getAnswer());
                Log.d("nextButton", "onClick: button pressed");
                counterTextView.setText(x +" out of "+y);






                break;

            case R.id.prevImage:
                if (counter==0){
                    Toast.makeText(this, "you are on first question", Toast.LENGTH_SHORT).show();



                }
                else {
                    counter = counter - 1;
                    x= x-1;
                    questionTextView.setText(questionsList.get(counter).getAnswer());
                    Log.d("prevButton", "onClick: button pressed");
                    counterTextView.setText(x +" out of "+y);

                }
                break;


            case R.id.buttonTrue:
                Log.d("trueButton", "onClick: "+questionsList.get(counter).isAnswerTrue());
               // if (questionsList.get(counter).isAnswerTrue())
                if (questionsList.get(counter).isAnswerTrue()==isTrue) {

                    Toast.makeText(this, "correct Answer", Toast.LENGTH_SHORT).show();

                    shakeAnimation();





                }
                else {


                    Toast.makeText(this, "wrong answer", Toast.LENGTH_SHORT).show();
                }








                break;

            case R.id.buttonFalse:
                if (questionsList.get(counter).isAnswerTrue()==isFalse) {

                    Toast.makeText(this, "correct Answer", Toast.LENGTH_SHORT).show();

                    shakeAnimation();


                }
                else {


                    Toast.makeText(this, "wrong answer", Toast.LENGTH_SHORT).show();
                }
                break;





        }
    }

  public void shakeAnimation(){


      cardView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
  }






}
