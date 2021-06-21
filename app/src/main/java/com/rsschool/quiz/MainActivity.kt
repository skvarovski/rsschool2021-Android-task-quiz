package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RouterQuizFragment, RouterResultFragment {

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
        //записываем текущий ответ
        questionList[indexQuestion].optionSelect = AnswerSelect
        indexQuestion++

        Log.d("TEST","Current IndexQuestion = ${indexQuestion}")
        //если это был последний вопрос, то
        if (questionList.size == indexQuestion) {
            doResult()
        } else {
            doNextQuiz(questionList[indexQuestion])
        }
    }


    //запуск результата опроса
    override fun doResult() {
        supportFragmentManager
            .beginTransaction()
            .replace(bi.mainFragment.id, ResultFragment())
            .commit()
    }

    override fun doPrevQuiz() {
        Log.d("TEST","GO Back")
        indexQuestion--
        doNextQuiz(questionList[indexQuestion])
    }

    override fun doShare() {
        TODO("Not yet implemented")
    }

    override fun doRestart() {
        super.onRestart()
    }

    override fun doClose() {
        super.onDestroy()
    }


    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

}