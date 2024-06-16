package com.dicoding.cinesuggest.view.Search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.databinding.ActivitySearchBinding
import com.dicoding.cinesuggest.view.Cinerec.Rec1Activity
import com.dicoding.cinesuggest.view.Home.HomeActivity
import com.example.cinemasuggest.data.adapter.Movie
import com.example.cinemasuggest.data.adapter.MovieAdapter
import com.example.cinemasuggest.data.room.AppDatabase
import com.example.cinemasuggest.data.room.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var db: AppDatabase
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cinema-suggest-db")
            .build()

        // Set the selected item in the bottom navigation
        binding.bottomNavigationView.selectedItemId = R.id.bottom_search

        // Get user name
        showProgressBar()
        getUserName()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(this@SearchActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        // Set up RecyclerView
        binding.rvRecommendedMovies.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        adapter = MovieAdapter(getDummyMovies())
        binding.rvRecommendedMovies.adapter = adapter

        // Set up bottom navigation
        setupBottomNavigation()
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

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@SearchActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_recommendation -> {
                    val intent = Intent(this@SearchActivity, Rec1Activity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_search -> {
                    // Stay on the SearchActivity
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

    private fun showProgressBar() {
        binding.loadingOverlay.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loadingOverlay.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }
}