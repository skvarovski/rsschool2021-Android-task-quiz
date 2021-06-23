package com.rsschool.quiz

import android.content.Intent
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

        initQuiz()
    }

    private fun initQuiz() {
        //инициализация настроек квиза
        indexQuestion = 0
        questionList = QuestionData().getQuestions()
        questionList.forEach{
            //костыль для зануления значений, видать глубже надо изучить :)
            it.optionSelect = null
        }

        // запуск Квиза
        doNextQuiz(questionList[indexQuestion])
    }

    // реализация интерфейса "следующего" экрана квиза
    override fun doNextQuiz(q: Question) {
        Log.d("TEST","Start Quiz. Current select = {${q?.optionSelect}}")
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

        //Log.d("TEST","Current IndexQuestion = ${indexQuestion}")
        //если это был последний вопрос, то
        if (questionList.size == indexQuestion) {
            doResult()
        } else {
            doNextQuiz(questionList[indexQuestion])
        }
    }


    private fun totalResult() =
        (questionList.count {
            it.optionSelect == it.optionRight
        }) * 100 / questionList.size


    //запуск результата опроса
    override fun doResult() {
        Log.d("TEST",questionList.toString())

        //вызов фрагмента с результатом
        supportFragmentManager
            .beginTransaction()
            .replace(bi.mainFragment.id, ResultFragment.newInstance(totalResult()))
            .commit()
    }

    override fun doPrevQuiz() {
        //Log.d("TEST","GO Back")
        indexQuestion--
        doNextQuiz(questionList[indexQuestion])
    }

    override fun doShare() {
        val text = buildString {
            append("Ваш результат законознания составил: ${totalResult()} ")
            append("\n\n")
            append("Ваши ответы:")
            questionList.forEachIndexed { index, question ->
                append("\n Вопрос: ${index+1}. ${question.question}")
                append("\n Ответ: ${question.optionAnswers[question.optionSelect!!-1]}")
                append("\n\n")
            }
        }

        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_SUBJECT,"Результаты квиза")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent,null)
        startActivity(shareIntent)

    }

    override fun doRestart() {
        initQuiz()
    }

    override fun doClose() {
        //super.onDestroy() - почему-то ошибка с этим вызовом. лупер эррор
        finishAndRemoveTask()
    }

    // отрабатываем кнопку "назад"
    override fun onBackPressed() {
        //Log.d("TEST","index Question = ${indexQuestion}")
        if (indexQuestion > 0) {
            doPrevQuiz()
        } else {
            doClose()
        }


    }


    override fun onDestroy() {
        super.onDestroy()

    }


}