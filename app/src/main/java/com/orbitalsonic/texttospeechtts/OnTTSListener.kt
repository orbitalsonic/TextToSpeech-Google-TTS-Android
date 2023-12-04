package com.orbitalsonic.texttospeechtts

interface OnTTSListener {
    fun onReadyForSpeak()
    fun onError(error: String)
}