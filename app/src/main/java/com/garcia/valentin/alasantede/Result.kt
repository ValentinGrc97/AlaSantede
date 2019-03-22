package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.result.*

/**
 * Created by valentin on 22/03/2019.
 */
class Result : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.result)

        var listUserNames = mutableListOf<String>()
        var tabScore = mutableListOf<Int>()
        var highscore = 0
        var positionHighscore = 0

        val prefs = getSharedPreferences("preferencename", 0)
        val size = prefs.getInt("list_user_size", 0)
        for (i in 0 until size) {

            listUserNames.add(prefs.getString("list_user_" + (i + 1).toString(), ""))
            tabScore.add(prefs.getInt("score_player" + (i + 1).toString(), 0))
            //TODO gérer égalité
            if (highscore < tabScore.get(i)) {
                highscore = tabScore.get(i)
                positionHighscore =  i
            }
        }
        nameLoser.text = listUserNames.get(positionHighscore)
    }
}