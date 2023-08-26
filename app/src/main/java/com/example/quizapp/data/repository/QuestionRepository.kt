package com.example.quizapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.data.model.QuestionListObject
import com.example.quizapp.data.retrofit.QuestionApi
import com.example.quizapp.data.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuestionRepository {

    private var questionApi: QuestionApi = RetrofitInstance().getRetrofitInstance()
        .create(QuestionApi::class.java)

    fun getQuestionsFromApi(): LiveData<QuestionListObject> {
        val mutableLiveData = MutableLiveData<QuestionListObject>()

        var questionList: QuestionListObject
        GlobalScope.launch(Dispatchers.IO) {
            val response = questionApi.getQuestions()

            // salvando os dados caso a resposta da api tenha dados
            questionList = response.body()!!

            // inserindo a lista na mutableLiveData, lembrando que em LiveData sempre
            // colocaremos o postValue para inserir dados em segundo plano.
            mutableLiveData.postValue(questionList)
            Log.i("TAGY", " " + mutableLiveData.value)
        }
        return mutableLiveData
    }

}