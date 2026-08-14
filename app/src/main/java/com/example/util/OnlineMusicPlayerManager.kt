package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.data.model.OnlineMusicTrack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MusicPlayerState(
    val currentTrack: OnlineMusicTrack? = null,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val errorMessage: String? = null
)

class OnlineMusicPlayerManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val _playerState = MutableStateFlow(MusicPlayerState())
    val playerState: StateFlow<MusicPlayerState> = _playerState.asStateFlow()

    private val progressHandler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let { mp ->
                if (mp.isPlaying) {
                    _playerState.value = _playerState.value.copy(
                        currentPositionMs = mp.currentPosition,
                        durationMs = mp.duration
                    )
                    progressHandler.postDelayed(this, 500)
                }
            }
        }
    }

    fun playTrack(track: OnlineMusicTrack) {
        // If same track is paused, resume it
        if (_playerState.value.currentTrack?.id == track.id && mediaPlayer != null) {
            if (!_playerState.value.isPlaying) {
                resume()
            }
            return
        }

        stop()

        _playerState.value = MusicPlayerState(
            currentTrack = track,
            isPlaying = false,
            isLoading = true,
            errorMessage = null
        )

        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(track.audioUrl)
                setOnPreparedListener { mp ->
                    _playerState.value = _playerState.value.copy(
                        isLoading = false,
                        isPlaying = true,
                        durationMs = mp.duration
                    )
                    mp.start()
                    progressHandler.post(progressRunnable)
                }
                setOnCompletionListener {
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        currentPositionMs = 0
                    )
                    progressHandler.removeCallbacks(progressRunnable)
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("OnlineMusicPlayer", "MediaPlayer error: $what, $extra")
                    _playerState.value = _playerState.value.copy(
                        isLoading = false,
                        isPlaying = false,
                        errorMessage = "خطا در پخش آهنگ آنلاین، اتصال اینترنت را بررسی کنید."
                    )
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e("OnlineMusicPlayer", "Failed to start media player", e)
            _playerState.value = _playerState.value.copy(
                isLoading = false,
                isPlaying = false,
                errorMessage = "خطا در برقراری اتصال به سرور موزیک"
            )
        }
    }

    fun pause() {
        try {
            mediaPlayer?.let { mp ->
                if (mp.isPlaying) {
                    mp.pause()
                    _playerState.value = _playerState.value.copy(isPlaying = false)
                    progressHandler.removeCallbacks(progressRunnable)
                }
            }
        } catch (e: Exception) {
            Log.e("OnlineMusicPlayer", "Error pausing", e)
        }
    }

    fun resume() {
        try {
            mediaPlayer?.let { mp ->
                mp.start()
                _playerState.value = _playerState.value.copy(isPlaying = true)
                progressHandler.post(progressRunnable)
            }
        } catch (e: Exception) {
            Log.e("OnlineMusicPlayer", "Error resuming", e)
        }
    }

    fun togglePlayPause(track: OnlineMusicTrack) {
        if (_playerState.value.currentTrack?.id == track.id) {
            if (_playerState.value.isPlaying) {
                pause()
            } else {
                resume()
            }
        } else {
            playTrack(track)
        }
    }

    fun seekTo(positionMs: Int) {
        try {
            mediaPlayer?.seekTo(positionMs)
            _playerState.value = _playerState.value.copy(currentPositionMs = positionMs)
        } catch (e: Exception) {
            Log.e("OnlineMusicPlayer", "Error seeking", e)
        }
    }

    fun stop() {
        progressHandler.removeCallbacks(progressRunnable)
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e("OnlineMusicPlayer", "Error stopping", e)
        }
        _playerState.value = MusicPlayerState()
    }

    fun release() {
        stop()
    }
}
