package com.example.ian.myawesomequiz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ian.myawesomequiz.QuizDBHelper;

public class AddQuestion extends AppCompatActivity {

    private EditText txtQuestion, txtOption1, txtOption2, txtOption3, txtOption4, txtAnswer;
    private Button btn_Simpan;

    QuizDBHelper dbHelper;

    private void setVariable(){
        dbHelper = new QuizDBHelper(this);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtOption1 = findViewById(R.id.txtOption1);
        txtOption2 = findViewById(R.id.txtOption2);
        txtOption3 = findViewById(R.id.txtOption3);
        txtOption4 = findViewById(R.id.txtOption4);
        txtAnswer = findViewById(R.id.txtAnswer);

        btn_Simpan = findViewById(R.id.btn_Simpan);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        setVariable();

        btn_Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String question = txtQuestion.getText().toString();
                String option1 = txtOption1.getText().toString();
                String option2 = txtOption2.getText().toString();
                String option3 = txtOption3.getText().toString();
                String option4 = txtOption4.getText().toString();
                String answer = txtAnswer.getText().toString();

                db.execSQL("INSERT INTO quiz_question(question, option1, option2, option3, option4, answer) VALUES " +
                        "('"+question+"', '"+option1+"', '"+option2+"', '"+option3+"', '"+option4+"', '"+answer+"')");

                Toast.makeText(AddQuestion.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ManageQuiz.class);
        startActivity(i);
    }
}
