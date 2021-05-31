package com.plcoding.musicia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.musicia.exoplayer.MusicService
import com.plcoding.musicia.exoplayer.MusicServiceConnection
import com.plcoding.musicia.exoplayer.currentPlaybackPosition
import com.plcoding.musicia.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    musicServiceConnection: MusicServiceConnection
): ViewModel() {
    private val playbackState = musicServiceConnection.playbackState

    private  val _currentSongDuration = MutableLiveData<Long>()
    val currentSongDuration :LiveData<Long> = _currentSongDuration

    private  val _currentPlayerPosition = MutableLiveData<Long>()
    val currentPlayerPosition :LiveData<Long> = _currentPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition(){
        viewModelScope.launch {
            while(true){
                val pos = playbackState.value?.currentPlaybackPosition
                if(currentPlayerPosition.value != pos){
                    _currentPlayerPosition.postValue(pos!!)
                    _currentSongDuration.postValue(MusicService.currentSongDuration)
                }
                delay(Constants.UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}