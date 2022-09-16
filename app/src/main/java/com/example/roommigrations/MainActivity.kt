package com.example.roommigrations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDatabase"
        )
            .addMigrations(UserDatabase.migrationFrom3To4)
            .build()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDatabase.userDao.getUsers().forEach(::println)
                userDatabase.schoolDao.getSchools().forEach(::println)
            }
        }

        (1..10).forEach {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userDatabase.userDao.insertUser(
                        User(
                            email = "user$it@example.com",
                            username = "user@it"
                        )
                    )
                    userDatabase.schoolDao.insertSchool(
                        School(
                            name = "school$it"
                        )
                    )
                }
            }
        }
    }
}