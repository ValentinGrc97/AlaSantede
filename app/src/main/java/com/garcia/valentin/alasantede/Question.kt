package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.garcia.valentin.alasantede.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.question.*
import android.content.SharedPreferences
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.gridview_layout.view.*




/**
 * Created by valentin on 22/03/2019.
 */
class Question : AppCompatActivity() {

    var listUserNames = mutableListOf<String>()
    var tabScore = mutableListOf<Int>()
    var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.question)

        val prefs = getSharedPreferences("preferencename", 0)
        val size = prefs.getInt("list_user_size", 0)
        for (i in 0 until size) {
            listUserNames.add(prefs.getString("list_user_" + (i + 1).toString(), ""))
            tabScore.add(prefs.getInt("score_player" + (i + 1).toString(), 0))
            lap += tabScore.get(i)
        }
        name.text = listUserNames.get(lap-1)
        gridview.adapter = GridViewAdapter(this, listUserNames)
    }

    fun onRadioButtonClicked(view: View) {
        for (i in 0 until listUserNames.size) {
            if(view.tag != null && view.tag.toString() == listUserNames.get(i)) {
                saveScore(i)
            }
        }
        if (lap < listUserNames.size) {
            startActivity(Intent(this, Question::class.java))
            finish();
        }
        else {
            startActivity(Intent(this, Result::class.java))
            finish();
        }
    }

    fun saveScore(i: Int): Boolean {
        tabScore.set(i, tabScore.get(i)+1)
        val prefs = getSharedPreferences("preferencename", 0)
        val editor = prefs.edit()
        editor.putInt("score_player" + (i+1).toString(), tabScore.get(i))
        return editor.commit()
    }
}