package com.miassolutions.quiztime.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizModel(
    var id : String ,
    var title: String ,
    var subTitle: String,
    var timer: Int,
    var questionList : List<QuestionModel>
) : Parcelable {
    constructor() : this("", "", "", 0, emptyList())
}
