package com.rsschool.quiz

interface RouterQuizFragment {
    fun doNextQuiz(q: Question)
    fun doNextWithResult(AnswerSelect: Int)
    fun doPrevQuiz()
    fun doResult()

}