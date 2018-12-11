package com.example.ian.myawesomequiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteQuestion extends AppCompatActivity {

    Button btnview, btnDelete;
    TextView txtDelet;
    //QuizDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);

        btnview = (Button) findViewById(R.id.showquestion);
        txtDelet=findViewById(R.id.textdel);
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowQuestion.class);
                startActivity(i);
            }
        });

        SharedPreferences prefs = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        txtDelet.setText(username);

        final QuizDBHelper dbHelper = new QuizDBHelper(this);
        btnDelete = findViewById(R.id.button_delquestion);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //String nama = txtDelet.getText().toString();
                db.execSQL("DELETE FROM quiz_question WHERE question = '"+txtDelet.getText().toString()+"'");
                Toast.makeText(DeleteQuestion.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            }


        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ManageQuiz.class);
        startActivity(i);
    }

    public void showMessage (String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
