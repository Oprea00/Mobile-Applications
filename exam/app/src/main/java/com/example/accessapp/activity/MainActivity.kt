package com.example.accessapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accessapp.R
import com.example.accessapp.logd
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.InternetConnection.Companion.context

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val staffButton = findViewById<Button>(R.id.section1)
        val employeeButton = findViewById<Button>(R.id.section2)
        val section3Button = findViewById<Button>(R.id.section3)

        staffButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }

        employeeButton.setOnClickListener{
            if (InternetConnection.getInstance(this).isOnline)
                startActivity(Intent(this@MainActivity, ManageActivity::class.java))
            else {
                Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                logd("No Internet Connection. Can't start Manage Activity")
            }
        }

        section3Button.setOnClickListener {
            if (InternetConnection.getInstance(this).isOnline)
                startActivity(Intent(this@MainActivity, ReportsActivity::class.java))
            else {
                Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                logd("No Internet Connection. Can't start Reports Activity")
            }
        }
    }
}