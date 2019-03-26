package com.garcia.valentin.alasantede

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.list_users.*
import java.util.concurrent.ThreadLocalRandom
import android.text.InputType
import android.widget.TextView
import android.widget.ScrollView



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
            if (listUserNames.size >= 3) {
                startActivity(Intent(this, Question::class.java))
            }
            else {
                Toast.makeText(this, R.string.error_nb_players, Toast.LENGTH_LONG).show()
            }
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
        editText.setHintTextColor(resources.getColor(R.color.white))
        editText.setTextColor(resources.getColor(R.color.white))
        editText.inputType = (InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        //cursor
        try {
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(editText, R.drawable.cursor_edittext)
        } catch (ignored: Exception) { }
        editText.background.setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.background.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
            } else {
                editText.background.setColorFilter(resources.getColor(R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP)
            }
        }
        editText.typeface = Typeface.create("serif-monospace", Typeface.NORMAL)
        editText.layoutParams = editTextLayoutParams
        editText.hint = getString(R.string.player) + (nbUsers+1).toString()
        rlListUsers.addView(editText)
        nbUsers++
        lastEditTextId = editText.id
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        buttonLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        addUser.layoutParams = buttonLayoutParams
        scrollView.post({ scrollView.fullScroll(ScrollView.FOCUS_DOWN) })
    }

    private fun getNames() {

        var i = 0

        listUserNames.clear()
        while (i < rlListUsers.childCount) {
            if (rlListUsers.getChildAt(i) is EditText) {
                val edittext = rlListUsers.getChildAt(i) as EditText
                if(edittext.text.toString() != "") {
                    listUserNames.add(edittext.text.toString())
                }
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