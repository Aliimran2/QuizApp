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

    private var countDownTimer: CountDownTimer? = null


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
        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
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


        }
    }

    private fun loadQuestions() {

        selectedAnswer = "" // reset selected answer for new question

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

    private fun enableAllAnswerButtons(enable: Boolean) {
        binding.apply {
            btn0.isEnabled = enable
            btn1.isEnabled = enable
            btn2.isEnabled = enable
            btn3.isEnabled = enable
        }
    }


    override fun onClick(view: View?) {

        if (view !is MaterialButton) return

        val clickedButton = view
        val correctAnswer = questionList[currentQuestionIndex].correct

        binding.apply {
            // Reset all button colors
            btn0.setBackgroundColor(getColor(R.color.grey))
            btn1.setBackgroundColor(getColor(R.color.grey))
            btn2.setBackgroundColor(getColor(R.color.grey))
            btn3.setBackgroundColor(getColor(R.color.grey))

            // If NEXT is clicked, go to next question
            if (clickedButton.id == R.id.btn_next) {
                currentQuestionIndex++
                loadQuestions()
                enableAllAnswerButtons(true)
                return
            }

            // Save selected answer
            selectedAnswer = clickedButton.text.toString()

            // Disable all answer buttons to prevent multiple selection
            enableAllAnswerButtons(false)

            // Check answer and color accordingly
            if (selectedAnswer == correctAnswer) {
                clickedButton.setBackgroundColor(getColor(R.color.green)) // correct
                score++
            } else {
                clickedButton.setBackgroundColor(getColor(R.color.red))   // wrong
                // Highlight correct button
                val correctButton = listOf(btn0, btn1, btn2, btn3)
                    .firstOrNull { it.text == correctAnswer }
                correctButton?.setBackgroundColor(getColor(R.color.green))
            }
        }
    }

    private fun finishQuiz() {

        if (!isFinishing && !isDestroyed){

            val dialog = ScoreDialogFragment.newInstance(score, questionList.size)
            dialog.show(supportFragmentManager, "ScoreDialogFragment")
        }


    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()

    }
}