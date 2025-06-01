package com.miassolutions.quiztime.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    var question : String,
    var options : List<String>,
    var correct : String
) : Parcelable {
    constructor() : this("", emptyList(), "")
}