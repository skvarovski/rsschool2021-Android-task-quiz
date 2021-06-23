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
    private var _bi: FragmentQuizBinding? = null
    private lateinit var bi: FragmentQuizBinding
    private lateinit var routerQuiz: RouterQuizFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routerQuiz = context as MainActivity
        //Log.d("TEST", "Start Quiz attach")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Log.d("TEST", "Start Quiz onCreateView")
        bi = FragmentQuizBinding.inflate(layoutInflater)
        return bi.root
    }

    private fun setThemeById(themeId:Int = 0) {
        when(themeId) {
            0 -> activity?.setTheme(R.style.Theme_Quiz_First)
            1 -> activity?.setTheme(R.style.Theme_Quiz_Two)
            2 -> activity?.setTheme(R.style.Theme_Quiz_Three)
            3 -> activity?.setTheme(R.style.Theme_Quiz_Four)
            4 -> activity?.setTheme(R.style.Theme_Quiz_Five)
            else -> activity?.setTheme(R.style.Theme_Quiz_First)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //_bi = FragmentQuizBinding.bind(view)
        //Log.d("TEST", "Start Quiz viewcreate")
        super.onViewCreated(view, savedInstanceState)
        bi.previousButton.text = getString(R.string.button_previous)
        bi.nextButton.text = getString(R.string.button_next)

        val currentQuiz = arguments?.getSerializable("BUNDLE_QUESTION") as? Question

        currentQuiz?.let {
            setThemeById(currentQuiz.theme)

            //проверка по Т3 и настройка первого экрана и остальных по порядковому номеру
            when (currentQuiz.id) {
                1 -> {
                    bi.toolbar.navigationIcon = null
                    bi.previousButton.isEnabled = false
                    bi.nextButton.isEnabled = false
                }
                2 -> {
                    bi.previousButton.isEnabled = true
                    bi.nextButton.isEnabled = false
                }
                3 -> {
                    bi.previousButton.isEnabled = true
                    bi.nextButton.isEnabled = false
                }
                4 -> {
                    bi.previousButton.isEnabled = true
                    bi.nextButton.isEnabled = false
                }
                5-> {
                    bi.previousButton.isEnabled = true
                    bi.nextButton.isEnabled = false
                    bi.nextButton.text = getString(R.string.button_submit)
                }
            }


            // назначение элементам view данных текущего дата-класса Question

            bi.toolbar.title = getString(R.string.question_number_title,currentQuiz.id)
            bi.optionOne.text = currentQuiz.optionAnswers[0]
            bi.optionTwo.text = currentQuiz.optionAnswers[1]
            bi.optionThree.text = currentQuiz.optionAnswers[2]
            bi.optionFour.text = currentQuiz.optionAnswers[3]
            bi.optionFive.text = currentQuiz.optionAnswers[4]
            bi.question.text = currentQuiz.question


            //если уже был ответ, то его нужно выставить заодно разблокировать кнопку?
            currentQuiz.optionSelect?.let {
                val currentCheckButton = bi.radioGroup.getChildAt(it) as? RadioButton
                currentCheckButton?.isChecked = true
                bi.nextButton.isEnabled = true
            }

        }

        //проверяем изменение среди кнопок для активации "далее"
        bi.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            bi.nextButton.isEnabled = true
        }

        // отлавливаем текущее значение при нажатии кнопки для слеюущего шага
        bi.nextButton.setOnClickListener {
            val checkButtonIn = bi.radioGroup.checkedRadioButtonId
            val checkButton = bi.radioGroup.findViewById<RadioButton>(checkButtonIn)
            val check = bi.radioGroup.indexOfChild(checkButton)+1

            //отсылаем результар в релизацию интерфейса
            routerQuiz.doNextWithResult(check)
        }

        //кнопка назад
        bi.previousButton.setOnClickListener {
            routerQuiz.doPrevQuiz()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bi = null
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