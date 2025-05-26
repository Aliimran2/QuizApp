package com.miassolutions.quiztime.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miassolutions.quiztime.data.QuestionModel

class QuizViewModel : ViewModel(){

    private val _questionModelList = MutableLiveData<List<QuestionModel>>()
    val questionModelList get() = _questionModelList


}