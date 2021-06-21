package com.rsschool.quiz

class QuestionData {
    open fun getQuestions(): List<Question> {
        return listOf(
            q1
        )
    }


    companion object {
        val q1 = Question(
            1,
            "Вас без маски остановила полиция, ваши действия?",
            arrayListOf(
                "Убежать",
                "Ругаться",
                "Одеть маску и повиноваться",
                "А Путин без маски тоже",
                "Я у мамы дурачок"),
            3,
            null,
            1)
    }
}