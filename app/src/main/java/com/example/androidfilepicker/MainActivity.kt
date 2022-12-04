package com.example.androidfilepicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.androidfilepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnReadStorage.setOnClickListener {
            val intent = Intent(this, ReadFilesActivity::class.java)
            startActivity(intent)
        }

        binding.btnWriteStorage.setOnClickListener {
            val intent = Intent(this, WriteFileActivity::class.java)
            startActivity(intent)
        }
    }
}