package com.example.musixia.Services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.os.PowerManager
import androidx.core.net.toUri

private const val ACTION_PLAY:String = "com.example.MUSIXIA"

class MusicService: Service(),MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    private var mediaPlayer:MediaPlayer?=null

    fun initMediaPlayer(){
        mediaPlayer!!.setOnErrorListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer!!.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action: String? = intent!!.action
        var data = intent.extras
        var url = data!!.getString("url")
        var uri = data.getString("uri")!!.toUri()

        when(action) {
            ACTION_PLAY -> {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.apply {
                    setAudioAttributes(
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    )

                    setDataSource(applicationContext,uri)
                    setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                    setOnPreparedListener(this@MusicService)
                    prepareAsync()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }


}




