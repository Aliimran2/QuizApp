package com.miassolutions.quiztime.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miassolutions.quiztime.R
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


        val quizList = mutableListOf<QuizModel>()
        quizList.add(QuizModel("Physics", "All about Physics", 10))
        quizList.add(QuizModel("Computer", "All about Computer", 20))
        quizList.add(QuizModel("Chemistry", "All about Chemistry", 15))
        quizList.add(QuizModel("Math", "All about Math", 10))
        quizList.add(QuizModel("Physics", "All about Physics", 10))
        quizList.add(QuizModel("Computer", "All about Computer", 20))
        quizList.add(QuizModel("Chemistry", "All about Chemistry", 50))
        quizList.add(QuizModel("Math", "All about Math", 10))

        quizListAdapter = QuizListAdapter{quiz ->
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(QUIZ, quiz)
            }
                startActivity(intent)

        }
        quizListAdapter.submit(quizList)
        binding.rvQuiz.adapter = quizListAdapter


    }
}