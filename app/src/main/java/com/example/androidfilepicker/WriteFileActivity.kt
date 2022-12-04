package com.example.androidfilepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.androidfilepicker.databinding.ActivityWriteFileBinding

class WriteFileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_file)
    }
}