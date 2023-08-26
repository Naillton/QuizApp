package com.example.quizapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        // acessando o companion object e retornando o valor de acertos das questoes
        // OBS: lembrar que o companion object é um objeto estatico
        binding.txtAnswer.text = "Your score is: "+MainActivity.result+"/"+MainActivity.totalQuestion

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}