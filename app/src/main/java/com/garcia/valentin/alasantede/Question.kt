package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.garcia.valentin.alasantede.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.question.*
import android.view.View
import com.garcia.valentin.alasantede.utils.*

/**
 * Created by valentin on 22/03/2019.
 */

class Question : AppCompatActivity() {

    private var lapPlayer = 1
    private var isAnswerSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.question)

        for (i in 0 until listUserNames.size) {
            lapPlayer += tabScore[i]
        }
        if (lapQuestion > listQuestions.size) {
            shuffleQuestions(this)
            lapQuestion = 1
        }
        name.text = listUserNames[lapPlayer-1].toUpperCase()
        question.text = listQuestions[lapQuestion-1]
        gridview.adapter = GridViewAdapter(this, listUserNames)

        if (lapPlayer > 1) {
            pass.visibility = View.INVISIBLE
        }
        pass.setOnClickListener {
            lapQuestion++
            startActivity(Intent(this, Question::class.java))
            finish()
        }
    }

    fun onRadioButtonClicked(view: View) {

        if(!isAnswerSelected) {
            isAnswerSelected = true
            (0 until listUserNames.size)
                    .filter { view.tag != null && view.tag == it }
                    .forEach { tabScore[it]++ }
            if (lapPlayer < listUserNames.size) {
                startActivity(Intent(this, Question::class.java))
                finish()
            } else {
                startActivity(Intent(this, Result::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        cleanScore()
    }
}