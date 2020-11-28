package com.shanu.musicplayer.Services

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.os.PowerManager

class MusicService:Service(),MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener{
    var mMediaPlayer:MediaPlayer?=null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent!!.action
        val data = intent.extras
        var name = data!!.getString("name")
        var artistName = data.getString("artist")
        val id = data.getLong("id")
        val uri: Uri = ContentUris.withAppendedId(android.provider
            .MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

        when(action){
            null -> {
                mMediaPlayer!!.reset()
                mMediaPlayer!!.apply {
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

}