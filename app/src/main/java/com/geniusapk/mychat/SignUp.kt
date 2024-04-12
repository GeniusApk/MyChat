package com.geniusapk.mychat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.geniusapk.mychat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        binding.btnsignup.setOnClickListener {
            val email = binding.emailsingup.editText?.text.toString()
                .trim()  // Get the email from the EditText
            val password = binding.passwordsingup.editText?.text.toString().trim()
            val name = binding.edtName.editText?.text.toString().trim()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUp(email, password, name)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Finish the current activity to prevent the user from coming back to the login/signup screen using the back button
    }


    private fun signUp(email: String, password: String, name: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success, update UI or navigate to the next screen
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                } else {
                    // If registration fails, display a message to the user.
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
       mDbRef = FirebaseDatabase.getInstance().getReference()
        val user = User(name, email, uid)
        mDbRef.child("user").child(uid).setValue(User(name , email, uid))
    }


}