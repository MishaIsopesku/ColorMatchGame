package com.example.colormatchgame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView colorText, timerText, questionText; //
    private Button yesButton, noButton;
    private int scoreCorrect = 0;
    private int scoreIncorrect = 0;
    private Random random = new Random(); // Генератор випадкових чисел
    private String[] colorNames = {"Червоний", "Синій", "Зелений", "Жовтий", "Чорний", "Білий"};
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.WHITE};
    private int currentColorIndex, currentTextColorIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        colorText = findViewById(R.id.colorText);
        timerText = findViewById(R.id.timerText);
        questionText = findViewById(R.id.questionText);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        startGame();

        // Обробники натискань на кнопки
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    private void startGame() {
        new CountDownTimer(180000, 1000) { // Таймер на 3 хв
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 60000);
                int seconds = (int) (millisUntilFinished % 60000 / 1000);
                timerText.setText("Час: " + minutes + ":" + String.format("%02d", seconds));
            }

            @Override// Закінчення таймера
            public void onFinish() {
                yesButton.setEnabled(false);
                noButton.setEnabled(false);
                timerText.setText("Час вийшов!");
                displayResults();
            }
        }.start();

        generateNewQuestion();
    }

    private void generateNewQuestion() {
        currentColorIndex = random.nextInt(colorNames.length);
        currentTextColorIndex = random.nextInt(colors.length);

        colorText.setText(colorNames[currentColorIndex]);
        colorText.setTextColor(colors[currentTextColorIndex]);
    }

    private void checkAnswer(boolean userSaidMatch) {
        boolean isMatch = (currentColorIndex == currentTextColorIndex); // Перевірка, чи відповідає текст кольору

        if (userSaidMatch == isMatch) {
            scoreCorrect++; //
        } else {
            scoreIncorrect++; //
        }

        generateNewQuestion(); //
    }

    private void displayResults() {
        // Виводимо результати гри
        colorText.setText("Правильні: " + scoreCorrect + "\nНеправильні: " + scoreIncorrect);
        colorText.setTextColor(Color.BLACK);
        questionText.setVisibility(View.GONE);
    }
}


