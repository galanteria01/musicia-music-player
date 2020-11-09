package com.example.musixia.Services

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.os.PowerManager
import android.util.Log

private const val ACTION_PLAY:String = "com.example.action.PLAY"

class MusicService: Service(),MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    private var mp:MediaPlayer?=null


    fun initMediaPlayer(){
        mp!!.setOnErrorListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
        Log.d("gay","This is beginning bullshit 3")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action: String? = intent!!.action
        Log.d("gay",action.toString())

        var data = intent.extras
        var url = data!!.getString("url")
        var id = data!!.getLong("id")
        var uri: Uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id )

        Log.d("gay","This is beginning bullshit 1")


        when(action) {
            null -> {
                mp = MediaPlayer()
                Log.d("gay","This is beginning bullshit 2")

                mp!!.apply {
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
        //initMediaPlayer()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.release()
    }


}




