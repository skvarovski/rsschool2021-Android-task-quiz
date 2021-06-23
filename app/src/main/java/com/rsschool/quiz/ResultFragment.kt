package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _bi: FragmentResultBinding? = null
    private val bi get() = _bi!!
    private lateinit var routerQuiz: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routerQuiz = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bi = FragmentResultBinding.inflate(inflater, container, false)
        val view = bi.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }



    override fun onDestroy() {
        super.onDestroy()
        _bi = null
    }


}