import android.content.Intent
import com.yogeshpaliyal.storagehelper.readers.PetiReader

object Peti {
    private val readers: ArrayList<PetiReader> = ArrayList()
    val sdkVersion = android.os.Build.VERSION.SDK_INT
    fun addReader(reader: PetiReader) {
        readers.add(reader)
    }

    fun openReader() {
       readers.firstOrNull {it.canRead(sdkVersion) }?.openReader()
    }

    fun handleReader(result: Intent) {
        readers.firstOrNull {it.canRead(sdkVersion) }?.handleResult(result)
    }
}