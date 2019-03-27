package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.garcia.valentin.alasantede.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.question.*
import android.view.View
import java.util.concurrent.ThreadLocalRandom


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
        val sizeUserTab = prefs.getInt("list_user_size", 0)
        val sizeQuestionTab = prefs.getInt("list_questions_size", 0)
        val lapQuestion = prefs.getInt("lapQuestion", 1)

        for (i in 0 until sizeUserTab) {
            listUserNames.add(prefs.getString("list_user_" + (i + 1).toString(), ""))
            tabScore.add(prefs.getInt("score_player" + (i + 1).toString(), 0))
            lapPlayer += tabScore[i]
        }
        if (lapQuestion > sizeQuestionTab) {
            shuffleQuestions()
        }
        question.text = prefs.getString("question_" + lapQuestion.toString(), "question")

        name.text = listUserNames[lapPlayer-1]
        gridview.adapter = GridViewAdapter(this, listUserNames)
    }

    fun onRadioButtonClicked(view: View) {

        if(!isAnswerSelected) {
            isAnswerSelected = true
            (0 until listUserNames.size)
                    .filter { view.tag != null && view.tag == it }
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

    private fun shuffleQuestions(): Boolean {

        val prefs = getSharedPreferences("preference", 0)
        val editor = prefs.edit()
        val questionsArray: Array<String>
        if (prefs.getString("difficulty", "soft") == "soft") {
            questionsArray = arrayOf(getString(R.string.worst_student), getString(R.string.most_intelligent),
                    getString(R.string.most_sleep), getString(R.string.most_roisterer),
                    getString(R.string.most_emotional), getString(R.string.most_alcoholic), getString(R.string.worst_alcoholic),
                    getString(R.string.most_beautiful_boy), getString(R.string.most_dredger), getString(R.string.worst_dredger),
                    getString(R.string.most_slackness), getString(R.string.most_drunk), getString(R.string.worst_drunk),
                    getString(R.string.most_extrovert), getString(R.string.most_loyal), getString(R.string.most_spew),
                    getString(R.string.most_lier))
        }
        else {
            questionsArray = arrayOf(getString(R.string.most_alcoholic), getString(R.string.worst_alcoholic),
                    getString(R.string.most_beautiful_boy), getString(R.string.most_dredger), getString(R.string.worst_dredger),
                    getString(R.string.most_slackness), getString(R.string.most_drunk), getString(R.string.worst_drunk),
                    getString(R.string.most_extrovert), getString(R.string.most_loyal), getString(R.string.most_spew),
                    getString(R.string.most_lier), getString(R.string.most_precocious), getString(R.string.best_shot),
                    getString(R.string.worst_shot), getString(R.string.most_apt_milf), getString(R.string.most_sex_like),
                    getString(R.string.most_apt_bit_h), getString(R.string.most_cheater), getString(R.string.most_sado),
                    getString(R.string.most_michto), getString(R.string.most_masturbate))
        }
        val rnd = ThreadLocalRandom.current()

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