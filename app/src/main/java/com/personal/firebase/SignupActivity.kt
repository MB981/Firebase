package com.personal.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.personal.firebase.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySignupBinding
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnSignUp.setOnClickListener {
            val email = mBinding.etEmail.text.toString()
            val password = mBinding.etPassword.text.toString()
            signupWithFirebase(email, password)

        }
    }

    private fun signupWithFirebase(userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@SignupActivity,
                    "Your account has been created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this@SignupActivity, task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}