package com.example.musixia

import android.net.Uri

class Music(){
    var uri:Uri?=null
    var name:String?=null
    var artist:String?=null
    var duration:Int?=null
    var size:Int?=null


    constructor(uri: Uri,
                name:String,
                artist:String,
                duration: Int,
                size:Int){
        this.uri = uri
        this.name = name
        this.artist = artist
        this.duration = duration
        this.size = size
    }

}