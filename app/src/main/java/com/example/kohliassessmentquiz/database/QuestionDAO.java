package com.example.kohliassessmentquiz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kohliassessmentquiz.utils.Questions;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface QuestionDAO {

    @Insert(onConflict = REPLACE)
    long[] insertQuestions(Questions... questions);


    @Query("SELECT * FROM Questions")
    LiveData<List<Questions>> getQuestions();



}
