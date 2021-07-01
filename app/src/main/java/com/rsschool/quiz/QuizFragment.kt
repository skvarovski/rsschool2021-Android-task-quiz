package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.InspectableProperty
import androidx.core.graphics.toColor
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {
    private var _bi: FragmentQuizBinding? = null
    private val bi: FragmentQuizBinding get() = _bi!!
    private lateinit var routerQuiz: RouterQuizFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routerQuiz = context as MainActivity
        //Log.d("TEST", "Start Quiz attach")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Log.d("TEST", "Start Quiz onCreateView")
        _bi = FragmentQuizBinding.inflate(layoutInflater)
        return bi.root
    }

    private fun setThemeById(themeId:Int = 0) {
        // применяем тему
        when(themeId) {
            0 -> activity?.setTheme(R.style.Theme_Quiz_First)
            1 -> activity?.setTheme(R.style.Theme_Quiz_Two)
            2 -> activity?.setTheme(R.style.Theme_Quiz_Three)
            3 -> activity?.setTheme(R.style.Theme_Quiz_Four)
            4 -> activity?.setTheme(R.style.Theme_Quiz_Five)
            else -> activity?.setTheme(R.style.Theme_Quiz_First)
        }
        // установка цвета статус бара
        val typeValueColor = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorPrimaryDark,typeValueColor,true)
        activity?.window?.statusBarColor = typeValueColor.data

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //_bi = FragmentQuizBinding.bind(view)
        //Log.d("TEST", "Start Quiz viewcreate")
        super.onViewCreated(view, savedInstanceState)
        bi.previousButton.text = getString(R.string.button_previous)
        bi.nextButton.text = getString(R.string.button_next)

        val currentQuiz = arguments?.getSerializable(FragmentKeys.KEY_QUEST.value) as? Question

        currentQuiz?.let {
            setThemeById(currentQuiz.theme)

            //проверка по Т3 и настройка первого экрана и остальных по порядковому номеру
            when (currentQuiz.id) {
                1 -> {
                    bi.toolbar.navigationIcon = null
                    bi.previousButton.isEnabled = false
                    bi.nextButton.isEnabled = false
                }
                in 2..4 -> {
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
        bi.radioGroup.setOnCheckedChangeListener { _, _ ->
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
        bi.toolbar.setNavigationOnClickListener {
            //Log.d("TEST","Click to shevron")
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
            fragment.arguments = bundleOf(Pair(FragmentKeys.KEY_QUEST.value,question)) //args
            return fragment
        }
    }

}