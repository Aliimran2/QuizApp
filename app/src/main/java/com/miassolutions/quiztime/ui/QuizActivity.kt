package com.miassolutions.quiztime.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.miassolutions.quiztime.R
import com.miassolutions.quiztime.data.QuestionModel
import com.miassolutions.quiztime.data.QuizModel
import com.miassolutions.quiztime.databinding.ActivityQuizBinding
import com.miassolutions.quiztime.databinding.DialogScoreBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }

    private lateinit var questionList: List<QuestionModel>


    private var score = 0
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""
    private var time: Int = 0

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
        quiz?.let {
            questionList = it.questionList
            time = it.timer
        }


        loadQuestions()
        buttonsClickListeners()
        startTimer()


    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60

                binding.tvCountDown.text = String.format("%02d:%02d", minutes, remainingSeconds)
                Log.d("MIAS_SOLUTIONS", String.format("%02d:%02d", minutes, remainingSeconds))
            }

            override fun onFinish() {
                finishQuiz()
            }
        }.start()

    }

    private fun buttonsClickListeners() {
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btnNext.setOnClickListener(this@QuizActivity)
            tvQuestion.setOnClickListener(this@QuizActivity)

        }
    }

    private fun loadQuestions() {

        selectedAnswer = ""

        if (currentQuestionIndex == questionList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            tvProgressIndicator.text = "Question ${currentQuestionIndex + 1}/${questionList.size}"
            val progress =
                (currentQuestionIndex.toFloat() / questionList.size.toFloat() * 100).toInt()
            progressBar.progress = progress

            tvQuestion.text = questionList[currentQuestionIndex].question
            btn0.text = questionList[currentQuestionIndex].options[0]
            btn1.text = questionList[currentQuestionIndex].options[1]
            btn2.text = questionList[currentQuestionIndex].options[2]
            btn3.text = questionList[currentQuestionIndex].options[3]


        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.grey))
            btn1.setBackgroundColor(getColor(R.color.grey))
            btn2.setBackgroundColor(getColor(R.color.grey))
            btn3.setBackgroundColor(getColor(R.color.grey))
        }

        val clickedButton = view as MaterialButton

        if (clickedButton.id == R.id.btn_next) {

            if (selectedAnswer == questionList[currentQuestionIndex].correct) {
                score++

            }

            currentQuestionIndex++
            loadQuestions()

        } else {
            selectedAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(getColor(R.color.primary))

        }

    }



    private fun finishQuiz() {

        val dialog = ScoreDialogFragment.newInstance(score, questionList.size)
        dialog.show(supportFragmentManager, "ScoreDialogFragment")

    }
}