package com.miassolutions.quiztime.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
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
    private lateinit var quizList: MutableList<QuizModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        quizList = mutableListOf()

        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {

                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        quizModel?.let { quizList.add(it) }

                    }
                            Log.d("MY_TAG", "$quizList")
                    quizListAdapter.submit(quizList)
                }
            }





        quizListAdapter = QuizListAdapter { quiz ->
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(QUIZ, quiz)
            }
            startActivity(intent)

        }

        binding.rvQuiz.layoutManager = LinearLayoutManager(this)
        binding.rvQuiz.adapter = quizListAdapter


    }


}