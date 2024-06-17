package com.example.cinemasuggest.data.room.recommendation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserMovieDao {
    @Insert
    fun insert(userMovie: UserMovie)

    @Query("SELECT * FROM user_movies WHERE title = :title LIMIT 1")
    fun getMovieByTitle(title: String): UserMovie?

    @Query("SELECT * FROM user_movies")
    fun getAllMovies(): List<UserMovie>

    @Query("DELETE FROM user_movies")
    fun deleteAllMovies()
}
