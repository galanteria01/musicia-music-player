package com.plcoding.musicia.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.plcoding.musicia.R
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject

class SwipeSongAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(layoutId = R.layout.list_item){

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