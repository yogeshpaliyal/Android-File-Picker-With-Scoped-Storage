package com.example.androidfilepicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.androidfilepicker.databinding.ActivityReadFilesBinding
import com.example.androidfilepicker.extensions.getFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.IOException

/**
 * @author Yogesh Paliyal
 * @since 28 June 2020
 */
class ReadFilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadFilesBinding
    private val REQUEST_CAMERA = 1
    private val REQUEST_GALLERY = 2
    private val REQUEST_FILE = 3

    private var capturedImage: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_read_files)

        binding.btnCamera.setOnClickListener {
            cameraClicked()
        }

        binding.btnGallery.setOnClickListener {
            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickIntent, REQUEST_GALLERY)
        }

        binding.btnFile.setOnClickListener {
            val fileIntent = Intent(Intent.ACTION_GET_CONTENT)
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
            fileIntent.type = "*/*"
            startActivityForResult(fileIntent, REQUEST_FILE)
        }
    }

    private fun cameraClicked(){
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {

                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    capturedImage = setImageUri()

                    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImage)
                    takePhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    startActivityForResult(takePhotoIntent,REQUEST_CAMERA)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                   token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }
            }).check();
    }

    private fun setImageUri(): Uri? {
        var imageUri: Uri? = null
        try {
            val folder: File? = getExternalFilesDir(null)
            if (folder?.exists() == false)
                folder.mkdirs()
            val file = File(folder, "Image_Tmp11.jpg")
            if (file.exists()) file.delete()
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val authprity: String =
                packageName + getString(R.string.file_provider_name)
            imageUri = FileProvider.getUriForFile(
                this, authprity,
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imageUri
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var uri = capturedImage
            if (data != null && data.data != null)
                uri = data.data

            val path: File? = getFile(uri)

            binding.txtFilePath.text = path?.path
            binding.img.setImageURI(uri)

        }
    }

}