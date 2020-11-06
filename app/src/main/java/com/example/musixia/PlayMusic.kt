package com.example.musixia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        ivBackMusic.setOnClickListener {
            var intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        ivShareMusic.setOnClickListener {

        }
    }

}