package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.result.*

/**
 * Created by valentin on 22/03/2019.
 */
class Result : AppCompatActivity() {

    private val listUserNames = mutableListOf<String>()
    private lateinit var mInterstitialAd: InterstitialAd
    private var lapQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.result)

        var highscore = 0
        val prefs = getSharedPreferences("preference", 0)
        val size = prefs.getInt("list_user_size", 0)
        var drinking = ""
        lapQuestion = prefs.getInt("lapQuestion", 1)

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mInterstitialAd = InterstitialAd(this).apply {
            //TODO : this is production id, need to be set before publication
            //adUnitId = getString(R.string.admob_pub_id)
            adUnitId = getString(R.string.test_admob_pub_id)
            loadAd(AdRequest.Builder().build())
            adListener = (object : AdListener() {
                override fun onAdClosed() {
                    startNewQuestion()
                }

                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)

                    Toast.makeText(applicationContext, "Vérifiez votre connexoin internet ...", Toast.LENGTH_SHORT).show()
                }
            })
        }

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
            if (lapQuestion%3 == 1) {
                isLoadedInterstitial()
            }
            else {
                cleanScore()
                startNewQuestion()
            }
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

    private fun startNewQuestion() {
        startActivity(Intent(this,Question::class.java))
        finish()
    }

    private fun isLoadedInterstitial() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
            cleanScore()
        }
        else {
            isLoadedInterstitial()
        }
    }
}