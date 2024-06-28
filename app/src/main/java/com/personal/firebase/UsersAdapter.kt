package com.personal.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.personal.firebase.databinding.ActivityUpdateUserBinding
import com.personal.firebase.databinding.UserItemBinding

class UsersAdapter(
    var context: Context,
    var userList: ArrayList<User>
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(val adapterBinding: UserItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.adapterBinding.tvName.text = userList[position].userName
        holder.adapterBinding.tvAge.text = userList[position].userAge.toString()
        holder.adapterBinding.tvEmail.text = userList[position].userEmail

        holder.adapterBinding.linearLayout.setOnClickListener {
            val intent = Intent(context, UpdateUserActivity::class.java)
            intent.putExtra("id", userList[position].userID)
            intent.putExtra("name", userList[position].userName)
            intent.putExtra("age", userList[position].userAge)
            intent.putExtra("email", userList[position].userEmail)
            context.startActivity(intent)
        }
    }

    fun getUserId(position: Int): String {
    return userList[position].userID
    }

}
