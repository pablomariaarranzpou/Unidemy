package com.example.unidemy.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;

public class ViewTest extends AppCompatActivity {
    private Button buttonA, buttonB, buttonC, buttonD;
    private TextView questionText, resultText;
    TestQuestion currentQuestion;
    List<TestQuestion> list;
    FirebaseFirestore firestore;
    private String courseID;
    private CardTest ct;
    int iqa = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<TestQuestion>();
        firestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_view_test);
        questionText = (TextView) findViewById(R.id.triviaQuestion);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);
        resultText = (TextView) findViewById(R.id.resultText);



        if (getIntent().hasExtra("selectedTest")) {
            ct = (CardTest) getIntent().getParcelableExtra("selectedTest");
        }
        questionText.setText(ct.getTest_title());

        Task<QuerySnapshot> documentReference = firestore.collection("Test").document(ct.getTestID())
                .collection("Question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<TestQuestion> retrieved_ac = new ArrayList<TestQuestion>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Preguntas", document.getId() + " => " + document.getData());
                                retrieved_ac.add(new TestQuestion(document.getString("question_txt"),document.getString("question_oa"),document.getString("question_ob"),document.getString("question_oc"),document.getString("question_od"),document.getString("question_answer")));
                            }

                            list.addAll(retrieved_ac);
                            currentQuestion = list.get(iqa);
                            updateQuestion();

                        } else {
                            Log.d("Preguntas", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void updateQuestion() {


        resultText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());

    }

    public void buttonA(View view) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
            buttonA.setBackgroundColor(Color.GREEN);
            //Check if user has not exceeds the que limit
            if (iqa < list.size() - 1) {


                disableButton();
                correctDialog();
            }
            else {
                finish();
            }
        }

        else {

            finish();

        }
    }

    //Onclick listener for sec button
    @SuppressLint("ResourceType")
    public void buttonB(View view) {
        if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {
            buttonB.setBackgroundColor(Color.GREEN);
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    //Onclick listener for third button
    @SuppressLint("ResourceType")
    public void buttonC(View view) {
        if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
            buttonC.setBackgroundColor(Color.GREEN);
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                finish();
            }
        } else {

            finish();
        }
    }

    //Onclick listener for fourth button
    @SuppressLint("ResourceType")
    public void buttonD(View view) {
        if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {
            buttonD.setBackgroundColor(Color.GREEN);
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }




    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    public void correctDialog() {
        final Dialog dialogCorrect = new Dialog(ViewTest.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        Button buttonNext = (Button) dialogCorrect.findViewById(R.id.dialogNext);



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorrect.dismiss();
                iqa++;

                currentQuestion = list.get(iqa);
                updateQuestion();
                resetColor();
                enableButton();
            }
        });
    }

    public void resetColor() {

        buttonA.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        buttonB.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        buttonC.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        buttonD.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
    }

    public void disableButton() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
    }
    public void enableButton() {
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }
}
