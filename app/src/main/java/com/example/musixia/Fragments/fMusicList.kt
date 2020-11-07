package com.example.musixia.Fragments

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.net.toUri
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
            val uriColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            if (cursor != null) {
                if (cursor.moveToFirst()) {


                    do {
                        val uri = cursor.getString(uriColumn)
                        val name = cursor.getString(nameColumn)
                        val artistName = cursor.getString(artistNameColumn)
                        val duration = cursor.getFloat(durationColumn)
                        val size = cursor.getFloat(sizeColumn)

                        //val contentUri: Uri = ContentUris.withAppendedId(
                        //        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        //        id)
                        //val thumbnail: Bitmap =
                        //        context!!.contentResolver.loadThumbnail(
                        //                contentUri, Size(640, 480), null)


                        // Save musics in list
                        musicList.add(Music(uri.toUri(), name, artistName, duration, size))

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
                        myView.ivPlayButton.text = "Start"
                    }else {


                        try {
                            val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                                setAudioAttributes(
                                        AudioAttributes.Builder()
                                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                                .build()
                                )
                                setDataSource(context!!, song.uri!!)
                                prepare()
                                start()
                            }
                            mediaPlayer!!.start()

                            //mp!!.reset()
                            //mp!!.prepare()
                            //mp!!.setDataSource(context,song.uri!!)

                            //if(!(mp!!.isPlaying)) {
                            //    mp!!.start()
                            //}
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