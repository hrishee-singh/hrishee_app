package com.hrisheekesh.hrishee.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hrisheekesh.hrishee.R

class LoginActivity : AppCompatActivity() {

    private lateinit var redirectToSignUp: TextView
    lateinit var editEmail: EditText
    private lateinit var editPass: EditText
    lateinit var btnLogin: Button
    lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            getSharedPreferences(getString(R.string.prefrence_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        setContentView(R.layout.activity_login)

        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        editEmail = findViewById(R.id.editEmailAddress)
        editPass = findViewById(R.id.editPassword)
        redirectToSignUp = findViewById(R.id.redirectToSignUp)
        btnLogin = findViewById(R.id.btnLogin)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        redirectToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = editEmail.text.toString()
        val pass = editPass.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                savePreferences()
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(intent)
            } else
                Toast.makeText(this, "Invalid Credentials ", Toast.LENGTH_SHORT).show()
        }
    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

}