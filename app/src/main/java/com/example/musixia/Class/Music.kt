package com.example.musixia.Class

class Music {
    var url:String?=null
    var name:String?=null
    var artist:String?=null
    var duration:Float?=null
    var size:Float?=null

    constructor(url: String,
                name:String,
                artist:String,
                duration: Float,
                size: Float){
        this.url = url
        this.name = name
        this.artist = artist
        this.duration = duration
        this.size = size
    }




}