package com.personal.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.personal.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    val userList = ArrayList<User>()
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
        retrieveDataFromDatabase()

    }

    private fun retrieveDataFromDatabase() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eachUser in snapshot.children) {
                    val user = eachUser.getValue(User::class.java)

                    if (user != null) {
                        userList.add(user)
                    }
                }
                usersAdapter = UsersAdapter(this@MainActivity, userList)
                mBinding.rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                mBinding.rvUsers.adapter = usersAdapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}