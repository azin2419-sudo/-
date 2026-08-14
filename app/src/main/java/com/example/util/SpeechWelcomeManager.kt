package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class SpeechWelcomeManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var pendingMessage: String? = null

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("SpeechWelcomeManager", "Failed to init TTS", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("SpeechWelcomeManager", "US English not supported, falling back to default")
                tts?.setLanguage(Locale.ENGLISH)
            }
            tts?.setSpeechRate(0.95f)
            tts?.setPitch(1.05f)
            isInitialized = true

            // If a welcome message was queued, speak it now
            pendingMessage?.let { msg ->
                speakLoudly(msg)
                pendingMessage = null
            }
        } else {
            Log.e("SpeechWelcomeManager", "TTS initialization failed status: $status")
        }
    }

    /**
     * Speaks the English welcome message:
     * "Hello my beautiful Fereshteh, welcome to your own application!"
     */
    fun speakWelcomeGreeting(partnerName: String = "Fereshteh") {
        val message = "Hello my beautiful Fereshteh, welcome to your own application!"
        speakLoudly(message)
    }

    fun speakCustom(message: String) {
        speakLoudly(message)
    }

    private fun speakLoudly(text: String) {
        if (!isInitialized || tts == null) {
            pendingMessage = text
            return
        }

        try {
            // Set stream volume if possible to ensure loud & clear playback
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            audioManager?.let { am ->
                val currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC)
                val maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                if (currentVol < (maxVol * 0.7f).toInt()) {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, (maxVol * 0.85f).toInt(), 0)
                }
            }

            val params = Bundle()
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)

            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "WELCOME_GREETING_UTTERANCE")
        } catch (e: Exception) {
            Log.e("SpeechWelcomeManager", "Error speaking welcome greeting", e)
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            Log.e("SpeechWelcomeManager", "Error shutting down TTS", e)
        }
    }
}
