package com.example.androidfilepicker.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private const val tag = "FileUtils"


fun Context.getPath(
    uri: Uri?
): String {
    return getFile(uri)?.path ?: ""
}

/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun Context.getExtension(uri: Uri?): String? {
    uri ?: return null
    val mime = MimeTypeMap.getSingleton()
    val extension = mime.getExtensionFromMimeType(getMimeType(uri))
    return extension ?: ""
}

fun Context.getMimeType(uri: Uri): String? {
    val cR: ContentResolver = contentResolver
    return cR.getType(uri)
}

/**
 * Get Original file name from Uri
 *  https://developer.android.com/training/secure-file-sharing/retrieve-info
 */
fun Context?.getFileName(uri: Uri?): String? {
    uri ?: return null
    return this?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        cursor.getString(nameIndex)
    }
}


/**
 * Get file size from uri
 *  https://developer.android.com/training/secure-file-sharing/retrieve-info
 */
fun Context?.getFileSize(uri: Uri): Long {
    return this?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        cursor.moveToFirst()
        cursor.getLong(nameIndex)
    } ?: -1
}

/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun OutputStream.copyInputStreamToFile(inputStream: InputStream?) {
    this.use { fileOut ->
        inputStream?.copyTo(fileOut)
    }
}


/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun getTimestampString(): String {
    val date = Calendar.getInstance()
    return SimpleDateFormat("yyyy MM dd hh mm ss", Locale.US).format(date.time).replace(" ", "")
}


/**
 * Create a new file from given uri and return it
 */
fun Context.getFile(uri: Uri?): File? {
    uri ?: return null

    val tmpFile = File(cacheDir, getFileName(uri) ?: "temp_file_name.${getExtension(uri)}")
    try {
        FileOutputStream(tmpFile).copyInputStreamToFile(contentResolver.openInputStream(uri))
        return tmpFile
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}