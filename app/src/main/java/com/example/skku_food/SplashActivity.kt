package com.example.skku_food

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skku_food.database.DatabaseCopier
import kotlinx.coroutines.*

class SplashActivity: AppCompatActivity(){

    private lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Manage Database
        job = CoroutineScope(Dispatchers.IO).launch{
            DatabaseCopier.copyAttachedDatabase(context = applicationContext)
        }

        runBlocking {
            job.join()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}