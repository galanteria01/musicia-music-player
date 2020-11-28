package com.shanu.musicplayer

class Music {
    var songName:String?=null
    var artistName:String?=null
    var songID:Long?=null
    var songLength:Int?=null

    constructor(songName:String,artistName:String,songID:Long,songLength:Int){
        this.songName = songName
        this.artistName = artistName
        this.songID = songID
        this.songLength = songLength
    }
}