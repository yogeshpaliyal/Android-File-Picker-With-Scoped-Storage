package com.yogeshpaliyal.storagehelper.readers

import android.content.Intent

class GetContentReader: PetiReader {
    override fun canRead(sdkVersion: Int): Boolean {
        return true
    }

    override fun openReader() {
        val fileIntent = Intent(Intent.ACTION_GET_CONTENT)
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        fileIntent.type = "*/*"
        startActivityForResult(fileIntent, REQUEST_FILE)
    }

    override fun handleResult(intent: Intent) {

    }
}