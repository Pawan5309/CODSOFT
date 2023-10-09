import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private int timeRemaining;
    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    private String[] questions = {
            "What is the capital of France?",
            "What is the largest planet in our solar system?",
            "Which gas do plants absorb from the atmosphere?"
    };

    private String[] correctAnswers = {
            "Paris",
            "Jupiter",
            "Carbon dioxide"
    };

    private String[][] answerOptions = {
            {"Paris", "London", "Berlin"},
            {"Mars", "Jupiter", "Saturn"},
            {"Oxygen", "Carbon dioxide", "Nitrogen"}
    };

    public QuizApp() {
        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel();
        add(questionLabel);

        options = new JRadioButton[3];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            add(options[i]);
        }

        submitButton = new JButton("Submit");
        add(submitButton);

        timerLabel = new JLabel();
        add(timerLabel);

        currentQuestionIndex = 0;
        score = 0;
        timeRemaining = 10; // Set the initial timer value in seconds

        showNextQuestion();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                showNextQuestion();
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    checkAnswer();
                    showNextQuestion();
                } else {
                    timerLabel.setText("Time remaining: " + timeRemaining + " seconds");
                }
            }
        });
        timer.start();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 3; i++) {
                options[i].setText(answerOptions[currentQuestionIndex][i]);
                options[i].setSelected(false);
            }
            currentQuestionIndex++;
            timeRemaining = 10; // Reset the timer for the next question
            timerLabel.setText("Time remaining: " + timeRemaining + " seconds");
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < 3; i++) {
            if (options[i].isSelected()) {
                if (options[i].getText().equals(correctAnswers[currentQuestionIndex - 1])) {
                    score++;
                }
                break;
            }
        }
    }

    private void showResult() {
        timer.stop();
        questionLabel.setText("Quiz Completed!");
        for (int i = 0; i < 3; i++) {
            options[i].setVisible(false);
        }
        submitButton.setVisible(false);
        timerLabel.setText("Final Score: " + score + " out of " + questions.length);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp().setVisible(true);
            }
        });
    }
}
