package com.example.musixia.Class

import android.net.Uri

class Music {
    var uri:Uri?=null
    var name:String?=null
    var artist:String?=null
    var duration:Float?=null
    var size:Float?=null

    constructor(uri: Uri,
                name:String,
                artist:String,
                duration: Float,
                size:Float){
        this.uri = uri
        this.name = name
        this.artist = artist
        this.duration = duration
        this.size = size
    }




}