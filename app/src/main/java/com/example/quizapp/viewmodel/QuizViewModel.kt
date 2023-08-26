package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.data.model.QuestionListObject
import com.example.quizapp.data.repository.QuestionRepository

class QuizViewModel: ViewModel() {

    private var repository: QuestionRepository = QuestionRepository()

    private var questionLiveData: LiveData<QuestionListObject> = repository.getQuestionsFromApi()

    fun getQuestionsFromLiveData(): LiveData<QuestionListObject> {
        return questionLiveData
    }

}