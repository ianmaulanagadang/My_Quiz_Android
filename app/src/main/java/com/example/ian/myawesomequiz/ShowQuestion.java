package com.example.ian.myawesomequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowQuestion extends AppCompatActivity {

    private ListView list_data;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    //public static String question;
    QuizDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        list_data = findViewById(R.id.list);
        dbHelper = new QuizDBHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from quiz_question",
                null);
        final ArrayList<QuestionList> questionList = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name =
                            c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_QUESTION));

                    questionList.add(new QuestionList(name));
                } while(c.moveToNext());
            }
        }
        MyCustomListAdapter adapter = new
                MyCustomListAdapter(this, questionList);
        list_data.setAdapter(adapter);

        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String question = questionList.get(position).getName();

                questionList.get(position).getName();
                Intent i = new Intent(getApplicationContext(),DeleteQuestion.class);

                i.putExtra(QuizContract.QuestionTable.COLUMN_QUESTION, String.valueOf(question));
                SharedPreferences prefs = getSharedPreferences("myData", MODE_PRIVATE);
                SharedPreferences.Editor mEditor = prefs.edit();
                mEditor.putString("username", question);
                mEditor.apply();

                startActivity(i);
            }
        });
    }
    }

