package com.personal.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.personal.firebase.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityForgotPasswordBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = "Forgot Password"
        mBinding.btnResetPassword.setOnClickListener {
            val email = mBinding.etEmailAddress.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                } else {
                    finish()
                }
            }
        }


    }
}