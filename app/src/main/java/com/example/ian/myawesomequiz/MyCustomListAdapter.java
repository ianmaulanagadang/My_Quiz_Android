package com.example.ian.myawesomequiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.ian.myawesomequiz.QuestionList;
import java.util.ArrayList;
import java.util.List;
public class MyCustomListAdapter extends ArrayAdapter <QuestionList>{

    private Context context;
        private List<QuestionList> questionList = new ArrayList<QuestionList>();
        public MyCustomListAdapter(Context context, ArrayList<QuestionList> list) {
            super(context, 0, list);
            this.context = context;
            questionList = list;
        }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem =
                    LayoutInflater.from(context).inflate(R.layout.custom_list,
                            parent, false);

        }
        QuestionList currentQuestionList =
                    questionList.get(position);
        TextView txt_name =
                listItem.findViewById(R.id.txt_question);

        txt_name.setText(currentQuestionList.getName());
        return listItem;
    }
}
