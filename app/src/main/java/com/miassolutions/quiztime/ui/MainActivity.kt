package com.miassolutions.quiztime.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miassolutions.quiztime.R
import com.miassolutions.quiztime.data.QuestionModel
import com.miassolutions.quiztime.data.QuizModel
import com.miassolutions.quiztime.databinding.ActivityMainBinding

const val QUIZ = "quiz"

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var quizListAdapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val questionList = mutableListOf<QuestionModel>()
        questionList.add(QuestionModel(
            "What is value g?",
            listOf("10", "9.8", "7.9", "10.8"),
            "9.8"
        ))
        questionList.add(QuestionModel(
            "What is your father name?",
            listOf("10", "9.8", "7.9", "10.8"),
            "9.8"
        ))
        questionList.add(QuestionModel(
            "What is your name?",
            listOf("10", "9.8", "7.9", "10.8"),
            "9.8"
        ))
        questionList.add(QuestionModel(
            "What is Physics?",
            listOf("10", "9.8", "7.9", "10.8"),
            "9.8"
        ))
        questionList.add(QuestionModel(
            "What is Mathematics?",
            listOf("10", "9.8", "7.9", "10.8"),
            "9.8"
        ))

        val quizList = mutableListOf<QuizModel>()
        quizList.add(
            QuizModel(
                "Physics", "All about Physics", 1,
                questionList
            )
        )
        quizList.add(QuizModel("Computer", "All about Computer", 20, questionList))
        quizList.add(QuizModel("Chemistry", "All about Chemistry", 15, questionList))


        quizListAdapter = QuizListAdapter { quiz ->
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(QUIZ, quiz)
            }
            startActivity(intent)

        }
        quizListAdapter.submit(quizList)
        binding.rvQuiz.adapter = quizListAdapter


    }
}