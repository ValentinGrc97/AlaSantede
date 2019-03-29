package com.garcia.valentin.alasantede

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.list_users.*
import android.text.InputType
import android.widget.TextView
import android.widget.ScrollView
import com.garcia.valentin.alasantede.utils.listQuestions
import com.garcia.valentin.alasantede.utils.listUserNames
import com.garcia.valentin.alasantede.utils.tabScore

/**
 * Created by valentin on 21/03/2019.
 */

class ListUsers : AppCompatActivity() {

    private var nbEditText = 3
    private var lastEditTextId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_users)

        lastEditTextId = editText3.id

        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()

        addUser.setOnClickListener {
            addEditText()
        }
        buttonSoft.setOnClickListener {
            editor.putString("difficulty", getString(R.string.soft)).apply()
            selectedDifficulty()
        }
        buttonHard.setOnClickListener {
            editor.putString("difficulty", getString(R.string.hard)).apply()
            selectedDifficulty()
        }
    }

    private fun addEditText(){

        val editText = EditText(this)
        val editTextLayoutParams = RelativeLayout.LayoutParams(editText3.layoutParams.width,
                editText3.layoutParams.height)
        val buttonLayoutParams = RelativeLayout.LayoutParams(addUser.layoutParams.width,
                addUser.layoutParams.height)

        editText.id = View.generateViewId()
        editTextLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        editTextLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        editText.setHintTextColor(resources.getColor(R.color.white))
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
        editText.setTextColor(resources.getColor(R.color.white))
        editText.inputType = (InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        //cursor
        try {
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(editText, R.drawable.cursor_edittext)
        } catch (ignored: Exception) { }
        editText.background.setColorFilter(resources.getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP)
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.background.setColorFilter(resources.getColor(R.color.colorAccent),
                        PorterDuff.Mode.SRC_ATOP)
            } else {
                editText.background.setColorFilter(resources.getColor(R.color.black),
                        android.graphics.PorterDuff.Mode.SRC_ATOP)
            }
        }
        editText.typeface = Typeface.create("serif-monospace", Typeface.NORMAL)
        editText.layoutParams = editTextLayoutParams
        editText.hint = getString(R.string.player) + (nbEditText+1).toString()
        rlListUsers.addView(editText)
        nbEditText++
        lastEditTextId = editText.id
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        buttonLayoutParams.addRule(RelativeLayout.BELOW, lastEditTextId)
        addUser.layoutParams = buttonLayoutParams
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun selectedDifficulty() {

        getUsers()
        listQuestions.shuffle()
        if (listUserNames.size >= 3) {
            startActivity(Intent(this, Question::class.java))
        }
        else {
            Toast.makeText(this, R.string.error_nb_players, Toast.LENGTH_LONG).show()
        }
    }

    private fun getUsers() {

        listUserNames.clear()
        for (i in 0 until rlListUsers.childCount) {
            if (rlListUsers.getChildAt(i) is EditText) {
                val edittext = rlListUsers.getChildAt(i) as EditText
                if(edittext.text.toString() != "") {
                    listUserNames.add(edittext.text.toString())
                    tabScore.add(0)
                }
            }
        }
    }
}