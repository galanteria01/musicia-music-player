package com.plcoding.musicia.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.plcoding.musicia.data.entity.Song
import com.plcoding.musicia.other.Constants
import kotlinx.coroutines.tasks.await

class Musicdata {
    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(Constants.SONG_COLLECTION)

    suspend fun getSongs(): List<Song> {
        return try{
            songCollection.get().await().toObjects(Song::class.java)
        }catch (e: Exception){
            emptyList<Song>()
        }
    }
}