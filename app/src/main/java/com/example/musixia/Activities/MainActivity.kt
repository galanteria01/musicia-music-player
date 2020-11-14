package com.example.musixia.Activities

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.musixia.Class.Music
import com.example.musixia.Fragments.fFavouriteFragment
import com.example.musixia.Fragments.fMusicList
import com.example.musixia.Fragments.fSearchMusic
import com.example.musixia.R
import com.example.musixia.Services.MusicService
import com.example.musixia.Services.MusicService.MyBinder
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var mp:MediaPlayer?=null
    var services:MusicService?=null
    var musicList = ArrayList<Music>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        val musicListFragment = fMusicList()
        val searchFragment = fSearchMusic()
        val favoriteFragment = fFavouriteFragment()


        val connection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binderr = service as MyBinder
                services = binderr.getService()
                }

            override fun onServiceDisconnected(name: ComponentName) {}
                }
        if(MusicService.isPlaying){
            songName.text = services!!.getSongName()
            artistName.text = services!!.getArtistName()
        }


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, musicListFragment)
            commit()
        }
        buHome.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, musicListFragment)
                addToBackStack(null)
                commit()
            }
        }
        buSearch.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, searchFragment)
                addToBackStack(null)
                commit()
            }
        }
        buFavourite.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, favoriteFragment)
                addToBackStack(null)
                commit()
            }
        }
        PlayMusicPath.setOnClickListener {
            val intent = Intent(applicationContext, PlayMusic::class.java)
            startActivity(intent)
        }

        playPauseBtn.setOnClickListener {

            if(MusicService.isPlaying){
                MusicService.pausePlaying(services!!.mp)
                playPauseBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            }else{
                //MusicService.continuePlaying(MusicService.len!!)
                playPauseBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)

            }
        }
    }

    var CONTACT_CODE = 1234
    fun checkPermissions(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), CONTACT_CODE)
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CONTACT_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission rejected", Toast.LENGTH_SHORT).show()

                }
            }else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}