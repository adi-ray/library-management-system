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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

private lateinit var etname : EditText
private lateinit var etEmail: EditText
private lateinit var etPassword: EditText
private lateinit var logindf:TextView
private lateinit var btnSignup: Button
private lateinit var auth: FirebaseAuth
private lateinit var mDbRef: DatabaseReference
private lateinit var loader: ProgressDialog
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        loader = ProgressDialog(this)
        etEmail = findViewById(R.id.RegisterEmailAddress)
        logindf = findViewById(R.id.loginTextdp)

        etname = findViewById(R.id.editTextTextPersonName)
        etPassword = findViewById(R.id.RegisterPassword)
        btnSignup = findViewById(R.id.buttonRegister)
        supportActionBar?.hide()
        logindf.setOnClickListener {
            val intent = Intent(this@SignUpActivity,LogInActivity::class.java)
            finish()
            startActivity(intent)

        }
        btnSignup.setOnClickListener {

            val name = etname.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            loader.setTitle("Signing up")
            loader.setMessage("Please wait")


            loader.show()

            if(email.isEmpty()){
                etEmail.setError("Required")
                Toast.makeText(this@SignUpActivity,"Kindly fill email details",Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }
            else if(password.isEmpty()){
                etPassword.setError("Required")
                Toast.makeText(this@SignUpActivity,"Kindly fill the password",Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }
            else if(name.isEmpty()){
                etname.setError("Required")
                Toast.makeText(this@SignUpActivity,"Kindly fill the password",Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }

            else signup(name,email,password)

        }

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun signup(name:String,email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loader.dismiss()
                    addUserToDatabase(name,email,auth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                    val user = auth.currentUser
                    Toast.makeText(this@SignUpActivity, "Authentication Successful", Toast.LENGTH_SHORT).show()

                } else {
                    loader.dismiss()
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }


}