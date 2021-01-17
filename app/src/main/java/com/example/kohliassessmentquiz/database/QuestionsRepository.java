package com.example.kohliassessmentquiz.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.kohliassessmentquiz.utils.Questions;

import java.util.List;

public class QuestionsRepository {

    private QuestionDatabase questionDatabase;

    public QuestionsRepository(Context context) {
        questionDatabase = QuestionDatabase.getInstance(context);
    }

    public void insertQuestions(){
        new InsertAsyncTask(questionDatabase.getQuestionsDAO());
    }

    public LiveData<List<Questions>> retrieveQuestions(){
        return questionDatabase.getQuestionsDAO().getQuestions();
    }
}
