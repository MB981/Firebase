package com.personal.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.personal.firebase.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUpdateUserBinding
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = "Update User"
        getAndSetData()
        onClick()
    }

    fun onClick() {
        mBinding.btnUpdateUser.setOnClickListener {
            updateData()
        }
    }

    fun getAndSetData() {
        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val email = intent.getStringExtra("email")
        mBinding.etUpdateName.setText(name)
        mBinding.etUpdateAge.setText(age)
        mBinding.etUpdateEmail.setText(email)
    }

    fun updateData() {
        val updateName = mBinding.etUpdateName.text.toString()
        val updateAge = mBinding.etUpdateAge.text.toString()
        val updateEmail = mBinding.etUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

//        We using the mapping for updating the value in the realtime firebase
        val userMap = mutableMapOf<String, Any>()
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updateEmail
        userMap["userID"] = userId

        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { tasks ->
            if (tasks.isSuccessful) {
                Toast.makeText(this, "The user has been updated", Toast.LENGTH_SHORT).show()

                finish()
            } else {
                Toast.makeText(this, tasks.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}