package com.example.musixia.Class

import android.graphics.Bitmap
import android.net.Uri

class Music {
    var id:Long?=null
    var songURL:String?=null
    var uri:Uri?=null
    var name:String?=null
    var artist:String?=null
    var duration:Float?=null
    var size:Float?=null
    var thumbnailImage:Bitmap?=null

    constructor(id:Long,
                songURL:String,
                uri: Uri,
                name:String,
                artist:String,
                duration: Float,
                size: Float,
                thumbnailImage: Bitmap?){
        this.id = id
        this.uri = uri
        this.name = name
        this.artist = artist
        this.duration = duration
        this.size = size
        this.songURL = songURL
        this.thumbnailImage = thumbnailImage
    }




}