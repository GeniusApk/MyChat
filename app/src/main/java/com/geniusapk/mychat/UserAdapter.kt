package com.geniusapk.mychat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geniusapk.mychat.databinding.UserLayoutBinding
import com.google.firebase.auth.FirebaseAuth

// this is the adapter class for the recycler view

class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.txtName.text = currentUser.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context , ChatActivity::class.java)

            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)
        }


    }


    class UserViewHolder(val binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


    }

}