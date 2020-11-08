package com.example.musixia.Fragments

import android.annotation.SuppressLint
import android.content.ContentUris
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import android.view.View
import androidx.fragment.app.Fragment
import com.example.musixia.Class.Music
import com.example.musixia.R
import java.util.concurrent.TimeUnit


class fSearchMusic : Fragment(R.layout.fragment_f_search_music) {
    var listOfMusic = ArrayList<Music>()
    var searchedMusic = ArrayList<Music>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMusic()
        filterSearch()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun filterSearch(){
        for(i in listOfMusic){

        }
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


                        //val contentUri: Uri = ContentUris.withAppendedId(
                        //        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        //        id)
                        val thumbnail: Bitmap =
                                context!!.contentResolver.loadThumbnail(
                                        uri, Size(640, 480), null)


                        // Save musics in list
                        listOfMusic.add(Music(id,url,uri, name, artistName, duration, size,thumbnail))

                    }while (cursor.moveToNext())
                }

            }
        }


    }
}