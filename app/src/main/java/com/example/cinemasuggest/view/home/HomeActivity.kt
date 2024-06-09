package com.example.cinemasuggest.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.example.cinemasuggest.databinding.ActivityHomeBinding
import com.example.cinemasuggest.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import android.app.AlertDialog
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Logout
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        hideProgressBar()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, id ->
                showProgressBar()
                navigateToLogin()
                signOut()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun signOut() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val credentialManager = CredentialManager.create(this@HomeActivity)
                auth.signOut()
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
            }
        }
    }

    private fun navigateToLogin() {
        showProgressBar()
        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}
