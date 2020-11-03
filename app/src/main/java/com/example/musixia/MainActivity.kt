package com.example.musixia

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.music_list_ticket.view.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var musicList = mutableListOf<Music>()
    var adapter:MyMusicAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
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
        loadMusic()
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

    fun loadMusic(){


        val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
                )

        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(1,TimeUnit.MINUTES)
                .toString())
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val query =  contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder)

        query?.use{
            cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)


            while(cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val artistName = cursor.getString(artistNameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                )

                // Save musics in list
                musicList.add(Music(contentUri,name,artistName,duration.toFloat(),size.toFloat()))

            }
        }
        adapter = MyMusicAdapter(musicList)

    }

    inner class MyMusicAdapter: BaseAdapter{
        var listOfMusic = mutableListOf<Music>()
        constructor(listOfMusic:MutableList<Music>):super(){
            this.listOfMusic = listOfMusic

        }
        override fun getCount(): Int {
             return musicList.size
        }

        override fun getItem(position: Int): Any {
            return musicList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var song = musicList[position]
            if(musicList.isEmpty()){
                var myView = layoutInflater.inflate(R.layout.no_songs_ticket,null)
                return myView
            }else{
                var myView = layoutInflater.inflate(R.layout.music_list_ticket,null)
                myView.tvArtistName.text = song.artist
                myView.tvSongName.text = song.name
                return myView
            }
        }

    }

}