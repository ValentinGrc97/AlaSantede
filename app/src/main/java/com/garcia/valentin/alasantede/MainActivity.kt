package com.garcia.valentin.alasantede

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSharedPreferences("preferencename", 0).edit().clear().apply()

        buttonStart.setOnClickListener {
            startActivity(Intent(this, ListUsers::class.java))
        }
    }
}
