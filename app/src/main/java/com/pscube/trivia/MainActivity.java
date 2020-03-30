package com.pscube.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
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

import static android.provider.Telephony.BaseMmsColumns.MESSAGE_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //intializing
    CardView cardView;
    TextView counterTextView;
    TextView questionTextView;
    TextView yourScore;
    TextView highestScore;
    Button trueButton;
    Button falseButton;
    ImageView nextImageView;
    ImageView prevImageView;
    List<questions> questionsList;
    int counter = 0;
    int x = 1;
    int y = 0;
    int score = 0;
    int intHighestScore ;
    boolean isTrue = true;
    boolean isFalse = false;
    private static  final  String MESSAGE_ID = "message_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        cardView = findViewById(R.id.cardView);
        counterTextView = findViewById(R.id.counterTextView);
        questionTextView = findViewById(R.id.questionTextView);
        trueButton = findViewById(R.id.buttonTrue);
        falseButton = findViewById(R.id.buttonFalse);
        nextImageView = findViewById(R.id.nextImage);
        prevImageView = findViewById(R.id.prevImage);
        yourScore = findViewById(R.id.yourScore);
        highestScore= findViewById(R.id.highestScore);


        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
        prevImageView.setOnClickListener(this);



          gettingQuestionsFromModel();

        Log.d("tag", "onCreate: "+score +" "+ intHighestScore);


            gettingSharedPrefData();

    }

//methods

    private void gettingQuestionsFromModel() {
        questionsList = new questionBank().getQuestions(new asyncanswer() {
            @Override
            public void processFinished(ArrayList<questions> questionsArrayList) {
                Log.d("happy1", "processFinished: " + questionsArrayList);
                questionTextView.setText(questionsArrayList.get(counter).getAnswer());
                y = questionsArrayList.size();

                counterTextView.setText(x + " out of " + y);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.nextImage:

                nextQuestion();

                break;

            case R.id.prevImage:
                if (counter == 0) {

                    youAreOnFirstQuestion();



                } else {

                  previousQuestion();
                }
                break;


            case R.id.buttonTrue:
                    if (questionsList.get(counter).isAnswerTrue() == isTrue) {

                    scoreOnCorrectAnswer();
                    shakeAnimation_right();
                    nextQuestion();

                } else {


                    shakeAnimation_wrong();
                }
                break;

            case R.id.buttonFalse:
                if (questionsList.get(counter).isAnswerTrue() == isFalse) {


                    scoreOnCorrectAnswer();
                    shakeAnimation_right();
                    nextQuestion();


                } else {


                    shakeAnimation_wrong();
                }
                break;


        }
    }

    private void youAreOnFirstQuestion() {
        Toast.makeText(this, "you are on first question", Toast.LENGTH_SHORT).show();
    }

    public void shakeAnimation_wrong() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        cardView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.cardBackground));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Toast.makeText(this, "wrong answer", Toast.LENGTH_SHORT).show();
    }

    public void shakeAnimation_right() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        cardView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.cardBackground));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        Toast.makeText(this, "correct Answer", Toast.LENGTH_SHORT).show();
    }

    public void scoreOnCorrectAnswer(){
        score = score + 10;
        yourScore.setText("your score :"+score);
        settingDataToSharedPref();

    }

    public  void  nextQuestion(){
        counter = counter + 1;
        x = x + 1;

        questionTextView.setText(questionsList.get(counter).getAnswer());
        Log.d("nextButton", "onClick: button pressed");
        counterTextView.setText(x + " out of " + y);
    }

    public void previousQuestion(){


        counter = counter - 1;
        x = x - 1;
        questionTextView.setText(questionsList.get(counter).getAnswer());
        Log.d("prevButton", "onClick: button pressed");
        counterTextView.setText(x + " out of " + y);

    }

    private void settingDataToSharedPref() {
        if (score>=intHighestScore) {
            SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("key", score);
            editor.apply();
        }
    }

    private void gettingSharedPrefData() {


            SharedPreferences preferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
            intHighestScore = preferences.getInt("key", 0);

             Log.d("tag", "gettingSharedPrefData: "+intHighestScore + score);




                highestScore.setText("Highest score: "+intHighestScore);







        }







    }
