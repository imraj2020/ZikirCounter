package com.cse.zikircounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cse.zikircounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.inecrementBtn.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext,"Button Selected",Toast.LENGTH_LONG).show()
        })


    }
}