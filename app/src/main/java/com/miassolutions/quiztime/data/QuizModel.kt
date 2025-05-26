package com.miassolutions.quiztime.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizModel(val title: String, val subTitle: String, val timer: Int) : Parcelable
