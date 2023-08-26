package com.example.quizapp.data.retrofit

import com.example.quizapp.data.model.QuestionListObject
import retrofit2.Response
import retrofit2.http.GET

interface QuestionApi {

    @GET("questions")
    suspend fun getQuestions(): Response<QuestionListObject>
}