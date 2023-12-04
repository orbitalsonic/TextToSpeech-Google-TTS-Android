package com.orbitalsonic.texttospeechtts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechConverter(private val context: Context, private val onTTSListener: OnTTSListener) {

    private val ttsTAG = "ttsTAG"

    private var textToSpeech: TextToSpeech? = null

    fun speakText(message:String,langCode:String){
        try {
            val locale = Locale(langCode)
            onStopTTS()
            textToSpeech = TextToSpeech(context) { status ->
                try {
                    if (status == TextToSpeech.SUCCESS) {
                        val result = textToSpeech?.setLanguage(locale)

                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            onTTSListener.onError("Language is not supported!")
                        } else {
                            speakNow(message)
                        }
                    } else {
                        onTTSListener.onError("Oops! Something went wrong. Try again, please!")
                    }
                }catch (ex:Exception){
                    onTTSListener.onError("Oops! Something went wrong. Try again, please!")
                    Log.e(ttsTAG, "${ex.message}")
                }
            }
        }catch (ex:Exception){
            onTTSListener.onError("Oops! Something went wrong. Try again, please!")
            Log.e(ttsTAG, "${ex.message}")
        }
    }

    private fun speakNow(text: String) {
        try {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }catch (ex:Exception){
            onTTSListener.onError("Oops! Something went wrong. Try again, please!")
            Log.e(ttsTAG, "${ex.message}")
        }
    }

    fun onStopTTS() {
        try {
            textToSpeech?.stop()
        }catch (ex:Exception){
            Log.e(ttsTAG, "${ex.message}")
        }
    }

    fun onShutdownTTS() {
        try {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }catch (ex:Exception){
            Log.e(ttsTAG, "${ex.message}")
        }
    }
}