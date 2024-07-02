package com.personal.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.personal.firebase.databinding.ActivitySignInWithPhoneNumberBinding
import java.util.concurrent.TimeUnit

class SignInWithPhoneNumberActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySignInWithPhoneNumberBinding
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var verificationCode: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignInWithPhoneNumberBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnVerify.setOnClickListener {
            signinWithSMSCode()
        }
        mBinding.btnSmsCode.setOnClickListener {
            val userPhoneNumber = mBinding.etPhoneNumber.text.toString()
            val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(userPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this@SignInWithPhoneNumberActivity)
                .setCallbacks(mCallbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationCode = p0
            }
        }
    }

    fun signinWithSMSCode() {
        val userEnterCode = mBinding.etVerifyCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(verificationCode, userEnterCode)
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this@SignInWithPhoneNumberActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@SignInWithPhoneNumberActivity,
                            "The code you entered is incorrect", Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}