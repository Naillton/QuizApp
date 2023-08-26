package com.example.quizapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.data.model.QuestionListObject
import com.example.quizapp.data.model.QuestionObject
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionListObject: List<QuestionObject>

    // criando companion object que sera usado para contar as questoes corretas na variavel result
    // e o total de questoes repondidas na totalQuestion
    companion object {
        var result = 0
        var totalQuestion = 0
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Resetando companion object
        result = 0
        totalQuestion = 0

        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        // exibindo primeira questao
        // usando o scopo globalde corrotinas com o dispatcher como Main para atualizar a view
        GlobalScope.launch(Dispatchers.Main) {
            // criando um oberver que ira verificar os dados retornados
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.size > 0) {
                    questionListObject = it
                    Log.i("TAGY", "RESULT "+questionListObject[0])

                    // criando bloco com o binding para nao precisar reescever o binding toda vez
                    // que for referenciar um elemento
                    binding.apply {
                        txtQuestion.text = questionListObject[0].question
                        radio1.text = questionListObject[0].response1
                        radio2.text = questionListObject[0].response2
                        radio3.text = questionListObject[0].response3
                    }
                }
            })
        }

        var i = 1
        binding.apply {
            btnNext.setOnClickListener( View.OnClickListener {
                val selectOptionRadio = radioGroup.checkedRadioButtonId

                // verificando se alguma opcao foi selecionada
                if (selectOptionRadio != -1) {
                    val radiobtn = findViewById<View>(selectOptionRadio) as RadioButton

                    // usando o questionsList para assim que tocar no botao mudar de qustao
                    questionListObject.let {

                        if (i < it.size) {
                            totalQuestion = it.size
                            // verificando se a questao selecionada esta correta
                            if (radiobtn.text.toString() == it[i - 1].correct) {
                                result++
                                txtResult.text = "Correct Answer"
                            }

                            // mostrando nova pergunta
                            txtQuestion.text = it[i].question
                            radio1.text = questionListObject[i].response1
                            radio2.text = questionListObject[i].response2
                            radio3.text = questionListObject[i].response3

                            // checnado ultima pergunta

                            if (i == it.size.minus(1)) {
                                btnNext.text = "FINISH"
                            }

                            radioGroup.clearCheck()
                            i++
                        } else {
                            if (radiobtn.text.toString() == it[i - 1].correct) {
                                result++
                                txtResult.text = "Correct Answer"
                            }
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please select one option",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}