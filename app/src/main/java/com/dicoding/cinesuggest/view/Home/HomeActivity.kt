package com.dicoding.cinesuggest.view.Home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.databinding.ActivityHomeBinding
import com.dicoding.cinesuggest.view.Cinerec.Rec1Activity
import com.dicoding.cinesuggest.view.Detail.MovieDetailActivity
import com.dicoding.cinesuggest.view.Login.LoginActivity
import com.dicoding.cinesuggest.view.Search.SearchActivity
import com.example.cinemasuggest.data.adapter.Movie
import com.example.cinemasuggest.data.adapter.MovieAdapter
import com.example.cinemasuggest.data.room.AppDatabase
import com.example.cinemasuggest.data.room.User
import com.example.cinemasuggest.utils.OnSwipeTouchListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cinema-suggest-db")
            .build()

        // Set up bottom navigation
        setupBottomNavigation()

        // Get user name
        showProgressBar()
        getUserName()

        // Logout
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        // Set up RecyclerView
        recyclerView = binding.RvCarousel1
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = MovieAdapter(getDummyMovies())
        recyclerView.adapter = adapter

        // Set up swipe listener
        binding.root.setOnTouchListener(object : OnSwipeTouchListener(this@HomeActivity) {
            override fun onSwipeLeft() {
                val intent = Intent(this@HomeActivity, Rec1Activity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        })

        // Add click listener for trendingMov
        binding.trendingMov.setOnClickListener {
            val intent = Intent(this@HomeActivity, MovieDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDummyMovies(): List<Movie> {
        // Replace this with your actual data source
        return listOf(
            Movie("Evil Dead Rise",  R.drawable.movie),
            Movie("Evil Dead Rise",  R.drawable.movie),
            Movie("Evil Dead Rise",  R.drawable.movie)
        )
    }

    private fun getUserName() {
        val user = auth.currentUser ?: return
        val uid = user.uid

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Try to fetch from local database first
                val localUser = db.userDao().getUserById(uid)
                withContext(Dispatchers.Main) {
                    if (localUser != null) {
                        binding.tvUsername.text = localUser.name
                        hideProgressBar()
                    } else {
                        // Fetch from Firestore if not in local database
                        firestore.collection("users").document(uid).get()
                            .addOnSuccessListener { document ->
                                hideProgressBar()
                                if (document != null) {
                                    val name = document.getString("name") ?: "Unknown User"
                                    binding.tvUsername.text = name
                                    saveUserToLocalDb(uid, name)
                                } else {
                                    binding.tvUsername.text = "Unknown User"
                                }
                            }
                            .addOnFailureListener {
                                hideProgressBar()
                                binding.tvUsername.text = "Error fetching name"
                            }
                    }
                }
            }
        }
    }

    private fun saveUserToLocalDb(uid: String, name: String) {
        val user = User(uid, name)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.userDao().insert(user)
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
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

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Stay on the HomeActivity
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_recommendation -> {
                    val intent = Intent(this@HomeActivity, Rec1Activity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_search -> {
                    val intent = Intent(this@HomeActivity, SearchActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_settings -> {
                    // Add intent for settings activity if exists
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun navigateToLogin() {
        showProgressBar()
        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showProgressBar() {
        binding.loadingOverlay.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loadingOverlay.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }
}