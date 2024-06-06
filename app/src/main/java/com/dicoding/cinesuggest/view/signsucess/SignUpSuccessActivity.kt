package com.dicoding.cinesuggest.view.signsucess

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cinesuggest.databinding.ActivitySignUpSucessBinding

class SignUpSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpSucessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpSucessBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}