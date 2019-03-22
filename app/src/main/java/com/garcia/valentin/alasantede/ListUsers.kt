package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.list_users.*

/**
 * Created by valentin on 21/03/2019.
 */
class ListUsers : AppCompatActivity() {

    var nbUsers = 3
    var lastEditTextId = 0
    var listUserNames = mutableListOf<String>()
    var tabScore = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_users)

        lastEditTextId = editText3.id

        addUser.setOnClickListener {
            addEditText()
        }

        buttonPlay.setOnClickListener {
            getNames()
            saveListUsers()
            startActivity(Intent(this, Question::class.java))
        }
    }

    fun addEditText(){
        val editText = EditText(applicationContext)
        editText.id = View.generateViewId()
        val editTextLayoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(editText3.layoutParams.width,
                editText3.layoutParams.height)
        editTextLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        editTextLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        editText.layoutParams = editTextLayoutParams
        editText.hint = getString(R.string.player) + (nbUsers+1).toString()
        rlListUsers.addView(editText)
        nbUsers++
        lastEditTextId = editText.id
        val buttonLayoutParams : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(addUser.layoutParams.width,
                addUser.layoutParams.height)
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        buttonLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        addUser.layoutParams = buttonLayoutParams
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    fun getNames() {
        var i = 0
        while (i < rlListUsers.childCount) {
            if (rlListUsers.getChildAt(i) is EditText) {
                val edittext = rlListUsers.getChildAt(i) as EditText

                listUserNames.add(edittext.text.toString())
                tabScore.add(0)
            }
            i++
        }
    }

    fun saveListUsers(): Boolean {
        val prefs = getSharedPreferences("preferencename", 0)
        val editor = prefs.edit()
        editor.putInt( "list_user_size", listUserNames.size)
        for (i in 0 until listUserNames.size) {
            editor.putString("list_user_" + (i+1).toString(), listUserNames[i])
            editor.putInt("score_player" + (i+1).toString(), tabScore[i])
        }
        return editor.commit()
    }
}