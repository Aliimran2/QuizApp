package com.miassolutions.quiztime.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    val question : String,
    val options : List<String>,
    val correct : String
) : Parcelable