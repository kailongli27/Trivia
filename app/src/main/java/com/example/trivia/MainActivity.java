package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.example.trivia.util.Prefs;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.trivia.R.string.inititalScore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView counterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int questionIndex = 0;
    private List<Question> questionList;
    private int scoreCounter = 0;
    private Score score;
    private TextView scoreTextView;
    private Prefs prefs;
    private TextView highestScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question_textView);
        counterTextView = findViewById(R.id.counter_textView);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.previous_button);
        score = new Score();
        scoreTextView = findViewById(R.id.score_TextView);
        prefs = new Prefs(MainActivity.this);
        highestScoreTextView = findViewById(R.id.highestScore_TextView);

        nextButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        scoreTextView.setText("Current score: " + String.valueOf(score.getScore()));
        highestScoreTextView.setText("Highest Score: " + String.valueOf(prefs.getHighScore()));
        questionIndex = prefs.getSavedState();

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(questionIndex).getAnswer());
                counterTextView.setText(questionIndex + " out of " + questionList.size());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.previous_button:
                if (questionIndex > 0){
                    questionIndex = (questionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next_button:
                goNextQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userAnswer) {
        boolean answerIsTrue = questionList.get(questionIndex).isAnswerTrue();
        int toastMessageID = 0;
        if (userAnswer == answerIsTrue){
            fade();
            addPoints();
            toastMessageID = R.string.correct;
        } else {
            shakeAnimation();
            takeOffPoints();
            toastMessageID = R.string.incorrect;
        }
        Toast.makeText(this, toastMessageID, Toast.LENGTH_SHORT).show();
    }

    private void addPoints(){
        scoreCounter += 100;
        score.setScore(scoreCounter);
        scoreTextView.setText("Current score: " + String.valueOf(score.getScore()));
    }

    private void takeOffPoints(){
        scoreCounter -= 100;
        if (score.getScore() <= 0){
            scoreCounter = 0;
            score.setScore(scoreCounter);
        } else {
            score.setScore(scoreCounter);
        }
        scoreTextView.setText("Current score: " + String.valueOf(score.getScore()));
    }

    private void updateQuestion() {
        String question = questionList.get(questionIndex).getAnswer();
        questionTextView.setText(question);
        counterTextView.setText(questionIndex + " out of " + questionList.size());
    }

    private void fade(){
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void goNextQuestion(){
        questionIndex = (questionIndex + 1) % questionList.size();
        updateQuestion();
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.saveState(this.questionIndex);
        super.onPause();
    }
}
