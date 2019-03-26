package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.result.*

/**
 * Created by valentin on 22/03/2019.
 */
class Result : AppCompatActivity() {

    private val listUserNames = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.result)

        var highscore = 0
        val prefs = getSharedPreferences("preference", 0)
        val size = prefs.getInt("list_user_size", 0)
        var drinking = ""

        for (i in 0 until size) {

            listUserNames.add(prefs.getString("list_user_" + (i + 1).toString(), ""))
            val score = prefs.getInt("score_player" + (i + 1).toString(), 0)
            if (highscore < score) {
                highscore = score
                drinking = listUserNames[i]
            }
            else if (highscore == score && highscore > 0) {
                drinking = drinking.replace(" et ", ", ")
                drinking = drinking + " et " + listUserNames[i]
            }
        }
        nameLoser.text = drinking

        go_again.setOnClickListener{
            cleanScore()
            startActivity(Intent(this, Question::class.java))
            finish()
        }
    }

    private fun cleanScore(): Boolean {

        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()

        for (i in 0 until listUserNames.size) {
            editor.putInt("score_player" + (i+1).toString(), 0)
        }
        editor.putInt("lapQuestion", prefs.getInt("lapQuestion",1)+1)
        return editor.commit()
    }
}