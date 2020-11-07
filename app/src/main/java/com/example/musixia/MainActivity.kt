package com.example.musixia

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.musixia.Class.Music
import com.example.musixia.Fragments.fFavouriteFragment
import com.example.musixia.Fragments.fMusicList
import com.example.musixia.Fragments.fSearchMusic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mediaPlayer:MediaPlayer?=null
    var musicList = ArrayList<Music>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        val musicListFragment = fMusicList()
        val searchFragment = fSearchMusic()
        val favoriteFragment = fFavouriteFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, musicListFragment)
            commit()
        }
        buList.setOnClickListener {
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
            val intent = Intent(applicationContext,PlayMusic::class.java)
            startActivity(intent)
        }


    }


    var CONTACT_CODE = 1234
    fun checkPermissions(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),CONTACT_CODE)
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CONTACT_CODE ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Permission rejected",Toast.LENGTH_SHORT).show()

                }
            }else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }
    }





}