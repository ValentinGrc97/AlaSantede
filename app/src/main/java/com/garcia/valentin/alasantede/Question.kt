package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.garcia.valentin.alasantede.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.question.*
import android.view.View




/**
 * Created by valentin on 22/03/2019.
 */
class Question : AppCompatActivity() {

    private var listUserNames = mutableListOf<String>()
    private var tabScore = mutableListOf<Int>()
    private var lapPlayer = 1
    private var isAnswerSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.question)

        val prefs = getSharedPreferences("preference", 0)
        val size = prefs.getInt("list_user_size", 0)
        val lapQuestion = prefs.getInt("lapQuestion", 1)

        for (i in 0 until size) {
            listUserNames.add(prefs.getString("list_user_" + (i + 1).toString(), ""))
            tabScore.add(prefs.getInt("score_player" + (i + 1).toString(), 0))
            lapPlayer += tabScore[i]
        }
        question.text = prefs.getString("question_" + lapQuestion.toString(), "question")

        name.text = listUserNames[lapPlayer-1]
        gridview.adapter = GridViewAdapter(this, listUserNames)
    }

    fun onRadioButtonClicked(view: View) {

        if(!isAnswerSelected) {
            isAnswerSelected = true
            (0 until listUserNames.size)
                    .filter { view.tag != null && view.tag.toString() == listUserNames[it] }
                    .forEach { saveScore(it) }
            if (lapPlayer < listUserNames.size) {
                startActivity(Intent(this, Question::class.java))
                finish()
            } else {
                startActivity(Intent(this, Result::class.java))
                finish()
            }
        }
    }

    private fun saveScore(i: Int): Boolean {

        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()

        tabScore[i] = tabScore[i]+1
        editor.putInt("score_player" + (i+1).toString(), tabScore[i])
        return editor.commit()
    }
}