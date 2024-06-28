package com.personal.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = usersAdapter.getUserId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                Toast.makeText(this@MainActivity, "The user is deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(mBinding.rvUsers)
    }

    private fun retrieveDataFromDatabase() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDialogMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Users")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.setPositiveButton("Yes") { _, _ ->
            myReference.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    usersAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "All users are deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete all users", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("No") { dialogInterface, i ->
            dialogInterface.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}