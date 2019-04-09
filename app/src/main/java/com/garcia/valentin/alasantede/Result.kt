package com.garcia.valentin.alasantede

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.garcia.valentin.alasantede.utils.cleanScore
import com.garcia.valentin.alasantede.utils.lapQuestion
import com.garcia.valentin.alasantede.utils.listUserNames
import com.garcia.valentin.alasantede.utils.tabScore
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.result.*

/**
 * Created by valentin on 22/03/2019.
 */

class Result : AppCompatActivity() {


    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.result)

        var highscore = 0
        var drinking = ""

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mInterstitialAd = InterstitialAd(this).apply {
            //TODO : this is production id, need to be set before publication
            //adUnitId = getString(R.string.admob_pub_id)
            adUnitId = getString(R.string.test_admob_pub_id)
            adListener = (object : AdListener() {
                override fun onAdClosed() {

                    cleanScore()
                    lapQuestion++
                    startNewQuestion()
                }

                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)

                    Toast.makeText(applicationContext, getString(R.string.error_internet),
                            Toast.LENGTH_SHORT).show()
                }
            })
        }
        loadAd()

        for (i in 0 until listUserNames.size) {
            if (highscore < tabScore[i]) {
                highscore = tabScore[i]
                drinking = listUserNames[i]
            }
            else if (highscore == tabScore[i] && highscore > 0) {
                drinking = drinking.replace(" et ", ", ")
                drinking = drinking + " et " + listUserNames[i]
            }
        }
        nameLoser.text = drinking.toUpperCase()
        go_again.setOnClickListener{
            if (lapQuestion%3 == 1) {
                showInterstitial()
            }
            else {
                cleanScore()
                lapQuestion++
                startNewQuestion()
            }
        }
    }

    private fun loadAd() {

        if (!mInterstitialAd.isLoading && !mInterstitialAd.isLoaded) {
            val adRequest = AdRequest.Builder().build()
            mInterstitialAd.loadAd(adRequest)
        }
    }

    private fun showInterstitial() {

        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            cleanScore()
            lapQuestion++
            startNewQuestion()
        }
    }

    private fun startNewQuestion() {

        startActivity(Intent(this,Question::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        cleanScore()
    }
}