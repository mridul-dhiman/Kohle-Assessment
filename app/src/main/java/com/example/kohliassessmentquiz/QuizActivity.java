package com.example.kohliassessmentquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohliassessmentquiz.database.QuestionsRepository;
import com.example.kohliassessmentquiz.utils.Questions;
import com.example.kohliassessmentquiz.utils.VerticalSpacing;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.platform.MaterialFadeThrough;

import java.util.ArrayList;

import static com.example.kohliassessmentquiz.utils.Constants.ANSWERED_CORRECTLY;
import static com.example.kohliassessmentquiz.utils.Constants.ANSWERED_INCORRECTLY;
import static com.example.kohliassessmentquiz.utils.Constants.VIEWED;

public class QuizActivity extends AppCompatActivity implements QuestionListAdapter.OnQuestionClickListener {
    private static final String TAG = "QuizActivity";

    private RecyclerView recyclerView;
    private QuestionListAdapter adapter;

    private ArrayList<Questions> questionsList = new ArrayList<>();
    private QuestionsRepository questionsRepository;

    private FloatingActionButton floatingActionButton;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private ArrayList<Integer> answeredList = new ArrayList<>();
    private ArrayList<Integer> responseList = new ArrayList<>();

    private TextView tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Integer pos = null, correct;
    private int corr, incorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.qAContainer)));
        getWindow().setExitTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.qAContainer)));
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        setContentView(R.layout.activity_quiz);
        recyclerView = findViewById(R.id.rvQuiz);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        questionsRepository = new QuestionsRepository(this);
        questionsRepository.insertQuestions();

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.questionBottomSheet));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        floatingActionButton.show();
                        checkAnswer();
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        floatingActionButton.hide();
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        findViewById(R.id.constraintLayoutBottomSheetHeader).setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));

        floatingActionButton.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("Confirm Submission");
            builder.setMessage("Are you sure you want to submit");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                dialog.cancel();
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putIntegerArrayListExtra("ANSWERED", responseList);
                intent.putExtra("CORR", corr);
                intent.putExtra("INCORR", incorr);
                startActivity(intent);
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        bottomSheetViews();

        retrieveQuestions();

        setupRecyclerView();
    }

    private void bottomSheetViews() {
        tvQuestion = findViewById(R.id.textViewQuestion);
        radioGroup = findViewById(R.id.radioGroupAnswer);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        Button buttonSubmitAnswer = findViewById(R.id.buttonSubmit);

        buttonSubmitAnswer.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    private void retrieveQuestions() {
        questionsRepository.retrieveQuestions().observe(this, questions -> {
            if (!questionsList.isEmpty()) {
                questionsList.clear();
            }
            if (!questions.isEmpty()) {
                questionsList.addAll(questions);
                for (int i = 0; i < questions.size(); i++) {
                    answeredList.add(null);
                    responseList.add(null);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void checkAnswer() {
        RadioButton response = findViewById(radioGroup.getCheckedRadioButtonId());
        if (response == null) {
            if (pos != null) {
                answeredList.set(pos, VIEWED);
            }
        } else {
            int selected = radioGroup.indexOfChild(response) + 1;
            if (selected == correct) {
                Log.d(TAG, "checkAnswer: " + pos + " LP " + answeredList.get(pos));
                answeredList.set(pos, ANSWERED_CORRECTLY);
                ++corr;
            } else {
                answeredList.set(pos, ANSWERED_INCORRECTLY);
                ++incorr;
            }
            responseList.set(pos, selected);
        }
        radioGroup.clearCheck();
        adapter.notifyDataSetChanged();
    }


    private void setupRecyclerView() {
        adapter = new QuestionListAdapter(questionsList, this, answeredList, responseList);
        adapter.setDisplayingQuestions(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpacing());
    }

    @Override
    public void OnClick(int position) {
        radioGroup.clearCheck();
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            if (answeredList.get(position) == null) {

                tvQuestion.setText(questionsList.get(position).getQuestion());
                rbOption1.setText(questionsList.get(position).getOption1());
                rbOption2.setText(questionsList.get(position).getOption2());
                rbOption3.setText(questionsList.get(position).getOption3());
                rbOption4.setText(questionsList.get(position).getOption4());

                pos = position;
                correct = questionsList.get(position).getCorrect_option();

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                Toast.makeText(this, "You have already viewed this question", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }
    }
}