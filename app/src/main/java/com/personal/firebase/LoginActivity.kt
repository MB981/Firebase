package com.personal.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.personal.firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnSignIn.setOnClickListener {
            val email = mBinding.etEmail.text.toString()
            val password = mBinding.etPassword.text.toString()
            signinWithFirebase(email, password)
        }
    }

    private fun signinWithFirebase(userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}