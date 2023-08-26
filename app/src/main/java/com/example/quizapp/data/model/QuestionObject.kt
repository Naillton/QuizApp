package com.example.quizapp.data.model

import java.util.UUID

data class QuestionObject(
    val id: UUID,
    val correct: String,
    val response1: String,
    val response2: String,
    val response3: String,
    val question: String
)
