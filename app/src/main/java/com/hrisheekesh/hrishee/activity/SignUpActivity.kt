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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrisheekesh.hrishee.R

class SignUpActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editConfPass: EditText
    private lateinit var editPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var redirectToLogin: TextView
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            getSharedPreferences(getString(R.string.prefrence_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        setContentView(R.layout.activity_sign_up)

        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        editEmail = findViewById(R.id.editEmailAddress2)
        editConfPass = findViewById(R.id.editConfPassword)
        editPass = findViewById(R.id.editPassword2)
        btnSignUp = findViewById(R.id.btnSSigned)
        redirectToLogin = findViewById(R.id.redirectToLogin)

        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            signUpUser()
        }

        redirectToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser() {
        val email = editEmail.text.toString()
        val pass = editPass.text.toString()
        val confirmPassword = editConfPass.text.toString()


        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                savePreferences()
                Toast.makeText(
                    this,
                    "Successfully Singed Up",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }
}