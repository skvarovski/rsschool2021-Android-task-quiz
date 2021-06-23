package com.rsschool.quiz

import java.io.Serializable

data class Question(
    val id: Int,
    val question: String,
    val optionAnswers: ArrayList<String>,
    val optionRight: Int,
    var optionSelect: Int?, // выбор ответа от пользователя
    val theme: Int
): Serializable