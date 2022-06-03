package com.example.unidemy.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewTest extends AppCompatActivity {
    private Button buttonA, buttonB, buttonC, buttonD;
    private TextView questionText, resultText;
    TestQuestion currentQuestion;
    List<TestQuestion> list;
    FirebaseFirestore firestore;
    private CardTest ct;
    int iqa = 0, correct = 0, incorrect = 0;

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

        if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
            buttonA.setBackgroundColor(Color.GREEN);
            correct++;
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            }
            else {
                testResumeDialog();
            }
        }
        else {
            buttonA.setBackgroundColor(Color.RED);
            incorrectDialog();
            incorrect++;

        }
    }

    //Onclick listener for sec button
    @SuppressLint("ResourceType")
    public void buttonB(View view) {
        if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {
            buttonB.setBackgroundColor(Color.GREEN);
            correct++;
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                testResumeDialog();
            }
        } else {
            buttonB.setBackgroundColor(Color.RED);
            incorrect++;
            incorrectDialog();
        }
    }


    public void buttonC(View view) {
        if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
            buttonC.setBackgroundColor(Color.GREEN);
            correct++;
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                testResumeDialog();
            }
        } else {
            buttonC.setBackgroundColor(Color.RED);
            incorrect++;
            incorrectDialog();


        }
    }

    public void buttonD(View view) {
        if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {
            buttonD.setBackgroundColor(Color.GREEN);
            correct++;
            if (iqa < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                testResumeDialog();
            }
        } else {
            buttonD.setBackgroundColor(Color.RED);
            incorrectDialog();
            incorrect++;

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

    public void incorrectDialog() {
        final Dialog dialogInCorrect = new Dialog(ViewTest.this);
        dialogInCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogInCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogInCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogInCorrect.setContentView(R.layout.dialog_incorrect);
        dialogInCorrect.setCancelable(false);
        dialogInCorrect.show();

        TextView incorrectNext = (TextView) dialogInCorrect.findViewById(R.id.resumeText);
        Button incorrectBtnNext = (Button) dialogInCorrect.findViewById(R.id.dialogNext);


        incorrectBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInCorrect.dismiss();
                iqa++;
                if(iqa == list.size()){
                    testResumeDialog();

                }else {
                    currentQuestion = list.get(iqa);
                    updateQuestion();
                    resetColor();
                    enableButton();
                }
            }
        });
    }

    public void testResumeDialog() {
        final Dialog dialogInCorrect = new Dialog(ViewTest.this);
        dialogInCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogInCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogInCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogInCorrect.setContentView(R.layout.dialog_resume_test);
        dialogInCorrect.setCancelable(false);
        dialogInCorrect.show();
        float nota = (float)correct / list.size() *10.0f;
        double roundOff = Math.round(nota * 100.0) / 100.0;
        TextView resumeText = (TextView) dialogInCorrect.findViewById(R.id.resumeText);
        if(roundOff < 5) {
            resumeText.setTextColor(Color.RED);
        }
        resumeText.setText("NOTA QUESTIONARIO:" + roundOff);

        Button nextResume = (Button) dialogInCorrect.findViewById(R.id.dialogNext);


        nextResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInCorrect.dismiss();
                finish();
            }
        });
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

        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.resumeText);
        Button buttonNext = (Button) dialogCorrect.findViewById(R.id.dialogNext);



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorrect.dismiss();
                iqa++;
                if(iqa == list.size()){
                    testResumeDialog();
                }else{
                    currentQuestion = list.get(iqa);
                    updateQuestion();
                    resetColor();
                    enableButton();
                }

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
