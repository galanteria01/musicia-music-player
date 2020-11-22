package com.example.musixia.Services

import android.app.Service
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.util.Log

private const val ACTION_PLAY:String = "com.example.action.PLAY"

class MusicService: Service(),MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    interface MusicManagerInterface{
        fun pausePlaying():Int
        fun startPlaying()
        fun continuePlaying(length: Int)
        fun stopPlaying()

    }
    var mp:MediaPlayer?=null
    var name:String?=null
    var artist:String?=null


    fun initMediaPlayer(){
        mp!!.setOnErrorListener(this)
    }
    var binder: MyBinder = MyBinder()
    var services: MusicService? = null
    var context: Context? = null
    var len:Int?=null

    override fun onBind(arg0: Intent?): IBinder? {
        // TODO Auto-generated method stub
        return binder
    }
    inner class MyBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }


    override fun onPrepared(mp: MediaPlayer?) {
        Log.d("start", "onPrepared")

        playSong()
    }
    fun getSongName():String{
        return "Playboy"
    }
    fun getArtistName():String{
        return artist!!
    }

    fun playSong(){
        mp!!.start()
    }
    fun pauseSong(){
        len = mp!!.currentPosition
        mp!!.pause()
    }
    fun continueSong(){
        mp!!.seekTo(len!!)
        mp!!.prepareAsync()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action: String? = intent!!.action
        Log.d("gay", action.toString())

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
                mp!!.reset()

                mp!!.apply {
                    setAudioAttributes(
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    )

                    setDataSource(applicationContext, uri)
                    setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                    setOnPreparedListener(this@MusicService)
                    prepareAsync()
                    Log.d("start", "prepareAsync")

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




    companion object {
        var isPlaying:Boolean=false
        var len:Int? = null

        fun pausePlaying(mp: MediaPlayer?):Int {
            mp!!.pause()
            len = mp!!.currentPosition;
            isPlaying = false
            return len as Int
        }

        fun startPlaying(mp: MediaPlayer?) {
            Log.d("start", "startPlaying")
            mp!!.start()
            isPlaying = true
        }

        fun continuePlaying(len: Int, mp: MediaPlayer?) {
            mp!!.seekTo(len);
            mp!!.start();
            isPlaying = true
        }

        fun stopPlaying(mp: MediaPlayer?){
            mp!!.stop()
            mp!!.release()
            isPlaying = false
        }
    }
}




