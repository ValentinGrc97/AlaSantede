package com.garcia.valentin.alasantede.utils

import android.content.Context
import com.garcia.valentin.alasantede.R

/**
 * Created by valentin on 29/03/2019.
 */

val listUserNames = mutableListOf<String>()
val tabScore = mutableListOf<Int>()
val listQuestions = mutableListOf<String>()
var lapQuestion = 1

fun shuffleQuestions(context: Context) {

    val prefs = context.getSharedPreferences("preference", 0)
    val difficulty = prefs.getString("difficulty", context.getString(R.string.soft))
    val questionsArray: Array<String>

    if (difficulty == "SOFT") {
        questionsArray = arrayOf(context.getString(R.string.worst_student),
                context.getString(R.string.most_intelligent),
                context.getString(R.string.most_sleep),
                context.getString(R.string.most_roisterer),
                context.getString(R.string.most_emotional),
                context.getString(R.string.most_alcoholic),
                context.getString(R.string.worst_alcoholic),
                context.getString(R.string.most_beautiful_boy),
                context.getString(R.string.most_dredger),
                context.getString(R.string.worst_dredger),
                context.getString(R.string.most_slackness),
                context.getString(R.string.most_drunk),
                context.getString(R.string.worst_drunk),
                context.getString(R.string.most_extrovert),
                context.getString(R.string.most_loyal),
                context.getString(R.string.most_spew),
                context.getString(R.string.most_lier))
    }
    else {
        questionsArray = arrayOf(context.getString(R.string.most_alcoholic),
                context.getString(R.string.worst_alcoholic),
                context.getString(R.string.most_beautiful_boy),
                context.getString(R.string.most_dredger),
                context.getString(R.string.worst_dredger),
                context.getString(R.string.most_slackness),
                context.getString(R.string.most_drunk),
                context.getString(R.string.worst_drunk),
                context.getString(R.string.most_extrovert),
                context.getString(R.string.most_loyal),
                context.getString(R.string.most_spew),
                context.getString(R.string.most_lier),
                context.getString(R.string.most_precocious),
                context.getString(R.string.best_shot),
                context.getString(R.string.worst_shot),
                context.getString(R.string.most_apt_milf),
                context.getString(R.string.most_sex_like),
                context.getString(R.string.most_apt_bit_h),
                context.getString(R.string.most_cheater),
                context.getString(R.string.most_sado),
                context.getString(R.string.most_michto),
                context.getString(R.string.most_masturbate))
    }
    (0 until questionsArray.size).mapTo(listQuestions) {
        questionsArray[it]
    }
    listQuestions.shuffle()
}

fun cleanScore() {

    for (i in 0 until tabScore.size) {
        tabScore[i] = 0
    }
}