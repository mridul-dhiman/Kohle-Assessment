package com.example.kohliassessmentquiz.database;

import android.util.Log;

import com.example.kohliassessmentquiz.utils.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertAsyncTask {
    private static final String TAG = "InsertAsyncTask";

    private final QuestionDAO questionDAO;

    private ArrayList<Questions> questionsArray = new ArrayList<>();

    public InsertAsyncTask(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
        IQ();
    }


    private void IQ() {
        questionsArray.add(new Questions(0, "What is the full form of RAM?", "Reliability, Availability and Maintainability",
                "Rarely Adequate Memory", "Raised Angle Marker", "Random Access Memory", 4));

        questionsArray.add(new Questions(1, "CPU stands for", "Control Processing Unit", "Central Processing Unit",
                "Call Processing Unit", "Cost Processing Unit", 2));

        questionsArray.add(new Questions(2, "Where is data stored in a computer?", "Mouse", "CPU", "Hard Disk", "RAM", 3));

        questionsArray.add(new Questions(3, "What is that input device used to type text and numbers on a document in the computer system?",
                "Mouse", "Monitor", "Printer", "Keyboard", 4));

        questionsArray.add(new Questions(4, "Name the computer part that helps a user to hear information from the system.", "Keyboard", "Speaker",
                "Mouse", "Monitor", 2));

        questionsArray.add(new Questions(5, "RAM is a _____ memory.", "Volatile", "Non-volatile", "Long", "Short", 1));

        questionsArray.add(new Questions(6, "What does ROM stand for?", "Rough-Order-of-Magnitude", "Read-Only Memory",
                "Read Only Media", "Read Only Member", 2));

        questionsArray.add(new Questions(7, "Which is the input device that allows a user to move the cursor or pointer on the screen?", "Keyboard", "Microphone",
                "Mouse", "Speaker", 3));

        questionsArray.add(new Questions(8, "_______ is an output device that displays information on the computer screen.", "Monitor", "Speaker",
                "Printer", "Keyboard", 1));

        questionsArray.add(new Questions(9, "What is the full form of LAN", "Light Access Network", "Long Access Network",
                "Local Access Network", "Local Area Network", 4));

        for (Questions questions : questionsArray) {
            InsertQuestion(questions);
        }
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void InsertQuestion(Questions... questionsList) {
        executorService.execute(() -> {
            long[] ret = questionDAO.insertQuestions(questionsList);
            Log.d(TAG, "InsertQuestion: " + Arrays.toString(ret));
        });
    }
}
