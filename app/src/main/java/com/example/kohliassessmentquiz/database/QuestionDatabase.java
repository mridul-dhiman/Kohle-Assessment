package com.example.kohliassessmentquiz.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kohliassessmentquiz.utils.Questions;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Questions_Database";

    private static QuestionDatabase instance;

    public static QuestionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), QuestionDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract QuestionDAO getQuestionsDAO();
}
