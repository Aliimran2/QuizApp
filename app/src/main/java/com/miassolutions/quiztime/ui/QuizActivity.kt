package com.miassolutions.quiztime.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miassolutions.quiztime.R
import com.miassolutions.quiztime.data.QuizModel
import com.miassolutions.quiztime.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val quiz = IntentCompat.getParcelableExtra<QuizModel>(intent, QUIZ, QuizModel::class.java)

        binding.textView.text = "${quiz.title}\n${quiz.subTitle}\n${quiz.timer}"
    }
}