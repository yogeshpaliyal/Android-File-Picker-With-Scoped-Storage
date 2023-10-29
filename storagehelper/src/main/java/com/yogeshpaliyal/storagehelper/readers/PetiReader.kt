package com.yogeshpaliyal.storagehelper.readers

import android.content.Intent

interface PetiReader {
    fun canRead(sdkVersion: Int): Boolean

    fun openReader()

    fun handleResult(intent: Intent)

}
