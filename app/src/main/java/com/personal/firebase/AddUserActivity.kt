package com.personal.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.personal.firebase.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAddUserBinding

    //    Make an instance of firebase to access the real time database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnAddUser.setOnClickListener {
            addUserToDatabase()
        }
        supportActionBar?.title = "Add User"

    }

    private fun addUserToDatabase() {

        val name: String = mBinding.etName.text.toString()
        val age: Int = mBinding.etAge.text.toString().toInt()
        val email: String = mBinding.etEmail.text.toString()

// unique key for each user
        val id: String = myReference.push().key.toString()
        val user = User(id, name, age, email)

//        Register user by its unique key
        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "The new user has been added to the database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }
}