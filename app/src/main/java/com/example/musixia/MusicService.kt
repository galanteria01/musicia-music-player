package com.example.musixia

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

private const val ACTION_PLAY: String = "com.example.action.PLAY"


class MusicService : Service(), MediaPlayer.OnPreparedListener {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    private var mMediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val action: String = intent.action!!
        when (action) {
            ACTION_PLAY -> {
                mMediaPlayer = MediaPlayer() // initialize it here
                mMediaPlayer?.apply {
                    setOnPreparedListener(this@MusicService)
                    prepareAsync() // prepare async to not block main thread
                }

            }
        }
        return null!!
    }

    /** Called when MediaPlayer is ready */
    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer!!.release()
    }


}