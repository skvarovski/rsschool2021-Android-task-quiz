package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RouterQuizFragment {

    private lateinit var bi: ActivityMainBinding
    private var indexQuestion = 0
    private var questionList = listOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)

        //инициализация настроек квиза
        indexQuestion = 0
        questionList = QuestionData().getQuestions()

        // запуск Квиза
        doNextQuiz(questionList[indexQuestion])


    }
    // реализация интерфейса "следующего" экрана квиза
    override fun doNextQuiz(q: Question) {
        Log.d("TEST","Start Quiz {$q}")
        supportFragmentManager
            .beginTransaction()
            .replace(bi.mainFragment.id, QuizFragment.newInstance(q))
            .commit()
    }
    //получаем результат ответа от текушего вопроса и сохраняем, далее готовимся к следующему вопросу
    override fun doNextWithResult(AnswerSelect: Int) {
        questionList[indexQuestion].optionSelect = AnswerSelect

    }



    override fun doPrevQuiz() {
        TODO("Not yet implemented")
    }



}