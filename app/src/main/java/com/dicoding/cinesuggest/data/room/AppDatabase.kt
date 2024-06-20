package com.dicoding.cinesuggest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dicoding.cinesuggest.data.room.auth.User
import com.dicoding.cinesuggest.data.room.auth.UserDao
import com.dicoding.cinesuggest.data.room.recommendation.UserMovie
import com.dicoding.cinesuggest.data.room.recommendation.UserMovieDao

@Database(entities = [User::class, UserMovie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userMovieDao(): UserMovieDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `user_movies` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `movieId` TEXT NOT NULL, `title` TEXT NOT NULL, `posterPath` TEXT NOT NULL)")
            }
        }
    }
}