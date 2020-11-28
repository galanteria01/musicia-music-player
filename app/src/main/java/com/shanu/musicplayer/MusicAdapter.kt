package com.shanu.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.music_ticket.view.*

class MusicAdapter(private val dataSet: MutableList<Music>):RecyclerView.Adapter<MusicAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val song = dataSet[viewType]
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.music_ticket,parent,false)
        myView.songName.text = song.songName
        myView.artistName.text = song.artistName
        return ViewHolder(myView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song = dataSet[position]
        holder.mySongName!!.text = song.songName
        holder.myArtistName!!.text = song.artistName

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var mySongName:TextView?=null
        var myArtistName:TextView?=null
        //val mySongImage:ImageView?=null
        init {
                mySongName = view.findViewById(R.id.songName)
                myArtistName = view.findViewById(R.id.artistName)
                //mySongImage = view.findViewById(R.id.ivSongImage)

        }
    }


}