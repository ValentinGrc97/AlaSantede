package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.list_users.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by valentin on 21/03/2019.
 */
class ListUsers : AppCompatActivity() {

    private var nbUsers = 3
    private var lastEditTextId = 0
    private var listUserNames = mutableListOf<String>()

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
            shuffleQuestions()
            startActivity(Intent(this, Question::class.java))
        }
    }

    private fun addEditText(){

        val editText = EditText(applicationContext)
        val editTextLayoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(editText3.layoutParams.width,
                editText3.layoutParams.height)
        val buttonLayoutParams : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(addUser.layoutParams.width,
                addUser.layoutParams.height)

        editText.id = View.generateViewId()
        editTextLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        editTextLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        editText.layoutParams = editTextLayoutParams
        editText.hint = getString(R.string.player) + (nbUsers+1).toString()
        rlListUsers.addView(editText)
        nbUsers++
        lastEditTextId = editText.id
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        buttonLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        addUser.layoutParams = buttonLayoutParams
        scrollView.fullScroll(View.FOCUS_DOWN)
    }

    private fun getNames() {

        var i = 0

        while (i < rlListUsers.childCount) {
            if (rlListUsers.getChildAt(i) is EditText) {
                val edittext = rlListUsers.getChildAt(i) as EditText
                listUserNames.add(edittext.text.toString())
            }
            i++
        }
    }

    private fun saveListUsers(): Boolean {

        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()

        editor.putInt( "list_user_size", listUserNames.size)
        for (i in 0 until listUserNames.size) {
            editor.putString("list_user_" + (i+1).toString(), listUserNames[i])
            editor.putInt("score_player" + (i+1).toString(), 0)
        }
        return editor.commit()
    }

    private fun shuffleQuestions(): Boolean {

        //Add questions here
        val questionsArray = arrayOf(getString(R.string.worst_student), getString(R.string.most_alcoholic),
                getString(R.string.most_beautiful_boy), getString(R.string.most_beautiful_girl),
                getString(R.string.most_intelligent), getString(R.string.most_dredger), getString(R.string.worst_dredger),
                getString(R.string.most_slackness))
        val rnd = ThreadLocalRandom.current()
        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()

        for (i in questionsArray.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = questionsArray[index]
            questionsArray[index] = questionsArray[i]
            questionsArray[i] = a
        }
        editor.putInt( "list_questions_size", questionsArray.size)
        for (i in 0 until questionsArray.size) {
            editor.putString("question_" + (i+1).toString(), questionsArray[i])
        }
        editor.putInt("lapQuestion", 1)
        return editor.commit()
    }
}