package com.miassolutions.quiztime.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
@Keep
@Parcelize
data class QuizModel(
    var id : String ,
    var title: String ,
    var subtitle: String,
    var timer: Int,
    var questionList : List<QuestionModel>
) : Parcelable {
    constructor() : this("", "", "", 0, emptyList())
}
