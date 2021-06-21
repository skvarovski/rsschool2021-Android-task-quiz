package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {
    private lateinit var bi: FragmentQuizBinding
    private lateinit var routerQuiz: RouterQuizFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routerQuiz = context as MainActivity
        Log.d("TEST", "Start Quiz attach")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TEST", "Start Quiz onCreateView")
        bi = FragmentQuizBinding.inflate(layoutInflater)
        return bi.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TEST", "Start Quiz viewcreate")
        super.onViewCreated(view, savedInstanceState)
        bi = FragmentQuizBinding.bind(view)
        val currentQuiz = arguments?.getSerializable("BUNDLE_QUESTION") as? Question

        currentQuiz?.let {



            //проверка по Т3 и настройка первого экрана и остальных по порядковому номеру
            when (currentQuiz.id) {
                1 -> {
                    bi.previousButton.isEnabled = false
                    bi.nextButton.isEnabled = false
                }
                2 -> {
                    bi.nextButton.isEnabled = false
                }
                3 -> {
                    bi.nextButton.isEnabled = false
                }
                4 -> {
                    bi.nextButton.isEnabled = false
                }
                5-> {
                    bi.nextButton.isEnabled = false
                }
            }
            // назначение элементам view данных текущего дата-класса Question

            bi.toolbar.title = "Вопрос №${currentQuiz.id}"
            bi.optionOne.text = currentQuiz.optionAnswers[0]
            bi.optionTwo.text = currentQuiz.optionAnswers[1]
            bi.optionThree.text = currentQuiz.optionAnswers[2]
            bi.optionFour.text = currentQuiz.optionAnswers[3]
            bi.optionFive.text = currentQuiz.optionAnswers[4]
            bi.question.text = currentQuiz.question
        }
        //проверяем изменение среди кнопок для активации "далее"
        bi.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            bi.nextButton.isEnabled = true
        }




        // отлавливаем текущее значение при нажатии кнопки для слеюущего шага
        bi.nextButton.setOnClickListener {
            val checkButtonIn = bi.radioGroup.checkedRadioButtonId
            val checkButton = bi.radioGroup.findViewById<RadioButton>(checkButtonIn)
            val check = bi.radioGroup.indexOfChild(checkButton)

            //отсылаем результар в релизацию интерфейса
            routerQuiz.doNextWithResult(check)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(question: Question): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putSerializable("BUNDLE_QUESTION", question)
            fragment.arguments = args
            return fragment
        }
    }

}