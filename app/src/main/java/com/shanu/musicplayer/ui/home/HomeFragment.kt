package com.shanu.musicplayer.ui.home

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shanu.musicplayer.Music
import com.shanu.musicplayer.MusicAdapter
import com.shanu.musicplayer.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment(R.layout.fragment_home) {
    var myAdapter:MusicAdapter?=null
    var listOfMusic=ArrayList<Music>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMusic()
        myAdapter = MusicAdapter(listOfMusic)
        RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        RecyclerView.adapter = myAdapter
    }

    private fun loadMusic() {
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
                .toString()
        )
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val query = requireContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            if (cursor != null) {
                if (cursor.moveToFirst()) {


                    do {
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn)
                        val artistName = cursor.getString(artistNameColumn)
                        val duration = cursor.getInt(durationColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        //val thumbnail: Bitmap =
                        //      context!!.contentResolver.loadThumbnail(
                        //               uri, Size(640, 480), null)


                        // Save musics in list
                        listOfMusic!!.add(Music(name, artistName, id, duration))

                    } while (cursor.moveToNext())
                }

            }
        }
    }
}