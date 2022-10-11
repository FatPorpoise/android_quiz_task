package com.example.quizheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizheroes.Question;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private int currentQuestion=0;
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
        setContentView(R.layout.activity_main);
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);
        Button nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton.setOnClickListener(view -> checkAnswerCorrectness(true));
        falseButton.setOnClickListener(view -> checkAnswerCorrectness(false));
        nextButton.setOnClickListener(view -> {
            currentQuestion = (currentQuestion +1)%questions.length;
            setNextQuestion();
        });
        setNextQuestion();
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentQuestion].getAnswer();
        int resultMessageId;
        if(userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
        }else{
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_LONG).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentQuestion].getQuestionId());
    }
}

