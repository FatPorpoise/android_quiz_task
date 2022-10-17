package com.example.quizheroes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizheroes.Question;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_QUESTION = "currentQuestion";
    public static final String KEY_EXTRA_ANSWER = "correctAnswer";
    public static final int REQUEST_CODE_PROMPT = 0;
    private TextView questionTextView;
    private int currentQuestion=0;
    private boolean answerWasShown = false;
    private Question[] questions = new Question[]{
            new Question(R.string.question1,false),
            new Question(R.string.question2,false),
            new Question(R.string.question3,true),
            new Question(R.string.question4,true),
            new Question(R.string.question5,false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            currentQuestion = savedInstanceState.getInt(KEY_CURRENT_QUESTION);
        }
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);
        Button nextButton = findViewById(R.id.next_button);
        Button helpButton = findViewById(R.id.help_button);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton.setOnClickListener(view -> checkAnswerCorrectness(true));
        falseButton.setOnClickListener(view -> checkAnswerCorrectness(false));
        nextButton.setOnClickListener(view -> {
            currentQuestion = (currentQuestion +1)%questions.length;
            answerWasShown = false;
            setNextQuestion();
        });
        helpButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentQuestion].getAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
        setNextQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null){return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
            }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState");
            outState.putInt(KEY_CURRENT_QUESTION, currentQuestion);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentQuestion].getAnswer();
        int resultMessageId;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_LONG).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentQuestion].getQuestionId());

    }
}

