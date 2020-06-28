package com.example.androidfilepicker.extensions

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private const val tag = "FileUtils"
/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun Context.getPath(
uri: Uri?
): String {

    if (uri == null)
        return ""

        try {

            // Store to tmp file
            val mFolder = File("$filesDir/Images")
            if (!mFolder.exists()) {
                mFolder.mkdirs()
            }


            val cR: ContentResolver = contentResolver
            val mime = MimeTypeMap.getSingleton()
            val extension = mime.getExtensionFromMimeType(cR.getType(uri))

            val tmpFile = File(mFolder.absolutePath, "IMG_${getTimestampString()}.${extension}")


            Log.d(tag,"tempFile => ${tmpFile.path}")

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(tmpFile)


                fos.copyInputStreamToFile(contentResolver.openInputStream(uri))
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return tmpFile.path
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return ""

}

/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun Context.getExtension(uri: Uri): String{
    val cR: ContentResolver = contentResolver
    val mime = MimeTypeMap.getSingleton()
    val extension = mime.getExtensionFromMimeType(cR.getType(uri))
    return extension ?: ""
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
@Throws(IOException::class)
fun Context.getBitmapFromUri(uri: Uri, options: BitmapFactory.Options? = null): Bitmap? {
    val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
    val fileDescriptor = parcelFileDescriptor?.fileDescriptor
    val image: Bitmap? = if (options != null)
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)
    else
        BitmapFactory.decodeFileDescriptor(fileDescriptor)
    parcelFileDescriptor?.close()
    return image
}

/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
fun getTimestampString(): String {
    val date = Calendar.getInstance()
    return SimpleDateFormat("yyyy MM dd hh mm ss", Locale.US).format(date.time).replace(" ", "")
}