package com.miassolutions.quiztime.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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


        setupRecyclerview()
        getQuizFromFirebase()


    }

    private fun setupRecyclerview() {

        quizListAdapter = QuizListAdapter { quiz ->
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(QUIZ, quiz)
            }
            startActivity(intent)

        }

        binding.rvQuiz.layoutManager = LinearLayoutManager(this)
        binding.rvQuiz.adapter = quizListAdapter
    }

    private fun getQuizFromFirebase() {
        binding.loading.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        quizModel?.let { quizList.add(it) }
                    }
                    // Submit the list to adapter
                    quizListAdapter.submit(quizList)
                    Log.d("MY_TAG", "$quizList")
                }
                binding.loading.visibility = View.GONE


            }
            .addOnFailureListener {
                Log.e("MainActivity", "Firebase load failed: ${it.message}")
                // Hide loader even if failed
                binding.loading.visibility = View.GONE
            }
    }



}