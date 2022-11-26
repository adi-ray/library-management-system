package com.example.elibrary

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var loader: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mAuth = FirebaseAuth.getInstance()
        loader = ProgressDialog(this)
        etEmail = findViewById(R.id.RegisterEmailAddress)
        etPassword = findViewById(R.id.RegisterPassword)
        btnSignup =findViewById(R.id.signUpText)
        btnLogin = findViewById(R.id.buttonRegister)

        supportActionBar?.hide()
        supportActionBar?.hide()
        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent);
            Toast.makeText(this,"Signup page",Toast.LENGTH_SHORT).show()
        }
        btnLogin.setOnClickListener {
            loader.setTitle("Logging in")
            loader.setMessage("Please wait")
            loader.show()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            if(email.isEmpty()){
                etEmail.setError("Required")
                Toast.makeText(this@LogInActivity,"Kindly fill email details",Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }
            else if(pass.isEmpty()){
                etPassword.setError("Required")
                Toast.makeText(this@LogInActivity,"Kindly fill the password",Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }

            else Login(email,pass)
        }

        val currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent = Intent(this@LogInActivity, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun Login(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loader.dismiss()
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LogInActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                    val user = mAuth.currentUser
                    Toast.makeText(this@LogInActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                }
                else {
                    loader.dismiss()
                    Toast.makeText(baseContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }

    }
}
