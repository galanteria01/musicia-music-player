package com.plcoding.musicia.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.plcoding.musicia.R
import kotlinx.android.synthetic.main.list_item.view.*

class SwipeSongAdapter : BaseSongAdapter(layoutId = R.layout.swipe_item){

    override var differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            val text = "${song.title} - ${song.subtitle}"
            tvPrimary.text = text
            setOnClickListener{
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}