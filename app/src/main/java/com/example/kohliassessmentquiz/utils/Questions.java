package com.example.kohliassessmentquiz.utils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Questions")
public class Questions {

    @PrimaryKey()
    private Integer id;

    @ColumnInfo(name = "Question")
    private String question;

    @ColumnInfo(name = "Option1")
    private String option1;

    @ColumnInfo(name = "Option2")
    private String option2;

    @ColumnInfo(name = "Option3")
    private String option3;

    @ColumnInfo(name = "Option4")
    private String option4;

    @ColumnInfo(name = "Correct_Option")
    private int correct_option;

    @Ignore
    public Questions() {
        //Empty constructor required
    }

    /*public Questions(String question, String option1, String option2, String option3, String option4, int correct_option) {
        Question = question;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
        this.correct_option = correct_option;
    }*/

    public Questions(Integer id, String question, String option1, String option2, String option3, String option4, int correct_option) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correct_option = correct_option;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(int correct_option) {
        this.correct_option = correct_option;
    }
}
