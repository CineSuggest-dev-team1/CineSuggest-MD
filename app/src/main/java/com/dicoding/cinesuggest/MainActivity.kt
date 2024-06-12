package com.dicoding.cinesuggest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.cinesuggest.view.Onboarding.OnboardingActivity
import com.dicoding.cinesuggest.view.recomendation.RecommendationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("com.dicoding.cinesuggest", MODE_PRIVATE)

        // Periksa apakah ini peluncuran pertama kali
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            // Jika peluncuran pertama kali, tampilkan onboarding
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false)
            editor.apply()

            // Panggil aktivitas OnboardingActivity
            val intent = Intent(this, RecommendationActivity::class.java)
            startActivity(intent)

            // Tutup MainActivity agar tidak kembali lagi saat menekan tombol kembali
            finish()
        }

    }
}