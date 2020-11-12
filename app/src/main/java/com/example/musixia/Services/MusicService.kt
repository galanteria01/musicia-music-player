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
    var name:String?=null
    var artist:String?=null


    fun initMediaPlayer(){
        mp!!.setOnErrorListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(mp: MediaPlayer?) {
        startPlaying()
    }
    fun getSongName():String{
        return name!!
    }
    fun getArtistName():String{
        return artist!!
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action: String? = intent!!.action
        Log.d("gay",action.toString())

        val data = intent.extras
        name = data!!.getString("name")
        artist = data.getString("artist")
        var url = data.getString("url")
        val id = data.getLong("id")
        val uri: Uri = ContentUris.withAppendedId(android.provider
                .MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)



        when(action) {
            null -> {
                mp = MediaPlayer()

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

    interface MusicManagerInterface{
        fun pausePlaying():Int
        fun startPlaying()
        fun continuePlaying(length:Int)
        fun stopPlaying()

    }


    companion object MusicManager : MusicManagerInterface {
        var isPlaying:Boolean=false
        var mp = MediaPlayer()
        var len:Int? = null
        override fun pausePlaying():Int {
            mp.pause()
            len = mp.currentPosition;
            isPlaying = false
            return len as Int
        }

        override fun startPlaying() {
            mp.start()
            isPlaying = true
        }

        override fun continuePlaying(len: Int) {
            mp.seekTo(len);
            mp.start();
            isPlaying = true
        }

        override fun stopPlaying(){
            mp.stop()
            mp.release()
            isPlaying = false
        }
    }
}




