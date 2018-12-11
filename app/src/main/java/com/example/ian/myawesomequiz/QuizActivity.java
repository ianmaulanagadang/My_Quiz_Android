package com.example.ian.myawesomequiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    public static final long COUNTDOWN_IN_MILLIS = 30000;
    public static final String EXTRA_SCORE = "extraScore";

    private TextView textScore;
    private TextView textLevel;
    private TextView textCountdown;
    private TextView textQuestion;
    private TextView textNumberQuestion;
    private RadioGroup rGroup;
    private RadioButton opt1;
    private RadioButton opt2;
    private RadioButton opt3;
    private RadioButton opt4;
    private Button buttonConfirm;

    private ColorStateList textColorDefault;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question> questionList;
    private int questionCount;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textScore = findViewById(R.id.text_score);
        textCountdown = findViewById(R.id.text_countdown);
        textQuestion = findViewById(R.id.text_question);
        textNumberQuestion = findViewById(R.id.text_number_question);
        rGroup = findViewById(R.id.radio_group);
        opt1 = findViewById(R.id.option_1);
        opt2 = findViewById(R.id.option_2);
        opt3 = findViewById(R.id.option_3);
        opt4 = findViewById(R.id.option_4);
        buttonConfirm = findViewById(R.id.button_next);

        textColorDefault = opt1.getTextColors();
        textColorDefaultCd = textCountdown.getTextColors();

        QuizDBHelper dbHelper = new QuizDBHelper(this);
        questionList = dbHelper.getQuestion();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if (opt1.isChecked() || opt2.isChecked() || opt3.isChecked() || opt4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this,"Pilih salah satu dong sayang",Toast.LENGTH_SHORT);
                    }
                } else
                    showNextQuestion();
            }
        });
    }

    private void showNextQuestion() {
        opt1.setTextColor(textColorDefault);
        opt2.setTextColor(textColorDefault);
        opt3.setTextColor(textColorDefault);
        opt4.setTextColor(textColorDefault);
        rGroup.clearCheck();

        if (questionCount < questionCountTotal) {
            currentQuestion = questionList.get(questionCount);

            textQuestion.setText(currentQuestion.getQuestion());
            opt1.setText(currentQuestion.getOption1());
            opt2.setText(currentQuestion.getOption2());
            opt3.setText(currentQuestion.getOption3());
            opt4.setText(currentQuestion.getOption4());

            questionCount++;
            textNumberQuestion.setText("Question: " + questionCount + "/" + questionCountTotal);
            answered = false;
            buttonConfirm.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            StartCountdown();
        } else {
            finishQuiz();
        }
    }
    private void StartCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int second =  (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format (Locale.getDefault(),"%02d:%02d",minutes,second);
        textCountdown.setText(timeFormatted);

        if (timeLeftInMillis < 10000){
            textCountdown.setTextColor(Color.RED);
        } else {
            textCountdown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer(){
        answered=true;

        countDownTimer.cancel();

        RadioButton optSelected = findViewById(rGroup.getCheckedRadioButtonId());
        int answerNr = rGroup.indexOfChild(optSelected) + 1;

        if (answerNr == currentQuestion.getAnswer()){
            score ++;
            textScore.setText("Score: " + score);
        }
        showSolution();
    }

    private void showSolution() {
        opt1.setTextColor(Color.RED);
        opt2.setTextColor(Color.RED);
        opt3.setTextColor(Color.RED);
        opt4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer()){
            case 1:
                opt1.setTextColor(Color.GREEN);
                textQuestion.setText("yang bener opsi a sob ");
                break;
            case 2:
                opt2.setTextColor(Color.GREEN);
                textQuestion.setText("wadaw yg bener yang b");
                break;
            case 3:
                opt3.setTextColor(Color.GREEN);
                textQuestion.setText("masyaallah yang bener ternyata c");
                break;
            case 4:
                opt4.setTextColor(Color.GREEN);
                textQuestion.setText("astaganagabonar jawabannya d");
                break;
        }
        if (questionCount < questionCountTotal) {
            buttonConfirm.setText("Lanjut");
        } else {
            buttonConfirm.setText("Selesai Quiz");
        }
    }

    private void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer !=null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu mo kluar?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(QuizActivity.this,StartingScreenActivity.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
