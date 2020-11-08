package com.example.musixia.Fragments

import android.annotation.SuppressLint
import android.content.ContentUris
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.musixia.Class.Music
import com.example.musixia.R
import kotlinx.android.synthetic.main.fragment_f_music_list.*
import kotlinx.android.synthetic.main.music_list_ticket.view.*
import java.util.concurrent.TimeUnit


class fMusicList : Fragment(R.layout.fragment_f_music_list) {
    var adapter:MyMusicAdapter?=null
    var musicList = ArrayList<Music>()
    var mp:MediaPlayer?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mp = MediaPlayer()
        loadMusic()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onDestroy() {
        super.onDestroy()
    }




    @SuppressLint("NewApi")
    fun loadMusic(){


        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
        )

        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
            .toString())
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val query =  context!!.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
             null,
            selection,
            null,
            null)

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            var urlColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            if (cursor != null) {
                if (cursor.moveToFirst()) {


                    do {
                        val id = cursor.getLong(idColumn)
                        var url = cursor.getString(urlColumn)
                        val name = cursor.getString(nameColumn)
                        val artistName = cursor.getString(artistNameColumn)
                        val duration = cursor.getFloat(durationColumn)
                        val size = cursor.getFloat(sizeColumn)
                        val uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id)
                        Log.d("Tagger",url)
                        Log.d("uriFind", uri.toString())


                        //val contentUri: Uri = ContentUris.withAppendedId(
                        //        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        //        id)
                        //val thumbnail: Bitmap =
                        //        context!!.contentResolver.loadThumbnail(
                        //                contentUri, Size(640, 480), null)


                        // Save musics in list
                        musicList.add(Music(id,url,uri, name, artistName, duration, size))

                    }while (cursor.moveToNext())
                }
                adapter = MyMusicAdapter(musicList)
                lvMusic.adapter = adapter
            }
        }


    }

    inner class MyMusicAdapter: BaseAdapter {
        var listOfMusic = ArrayList<Music>()
        constructor(listOfMusic:ArrayList<Music>):super(){
            this.listOfMusic = listOfMusic

        }
        override fun getCount(): Int {
            return this.listOfMusic.size
        }

        override fun getItem(position: Int): Any {
            return this.listOfMusic[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        fun pause(){
            mp!!.pause()
        }

        fun play(){
            mp!!.start()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var song = this.listOfMusic[position]


                if(this.listOfMusic.isEmpty()){
                var myView = layoutInflater.inflate(R.layout.no_songs_ticket,null)
                return myView
            }else{
                var myView = layoutInflater.inflate(R.layout.music_list_ticket,null)
                myView.tvArtistName.text = song.artist
                myView.tvSongName.text = song.name
                myView.ivPlayButton.setOnClickListener {
                    if(myView.ivPlayButton.text == "Stop"){
                        mp!!.stop()
                        myView.ivPlayButton.text = "Play"
                    }else {
                        mp = MediaPlayer()
                        try {

                            Toast.makeText(context,"trying music",Toast.LENGTH_SHORT).show()
                            Toast.makeText(context,"resetting music",Toast.LENGTH_SHORT).show()
                            Log.d("get",song.uri.toString())
                            mp!!.setDataSource(context!!, song.uri!!)
                            Toast.makeText(context,"setting music",Toast.LENGTH_SHORT).show()

                            mp!!.prepare()
                            Toast.makeText(context,"preparing music",Toast.LENGTH_SHORT).show()

                            Toast.makeText(context,"Starting music",Toast.LENGTH_SHORT).show()

                            mp!!.start()


                            myView.ivPlayButton.text = "Stop"
                        } catch (ex: Exception) {
                        }
                    }
                }
                return myView
            }
        }

    }

}
