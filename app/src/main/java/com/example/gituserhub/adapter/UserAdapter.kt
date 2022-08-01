package com.example.gituserhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gituserhub.R
import com.example.gituserhub.model.UserProfiles

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private val userList = ArrayList<UserProfiles>()
    private lateinit var onProfileCallback: OnProfileCallback

    interface OnProfileCallback {
        fun onProfileClicked(data: UserProfiles)
    }

    fun setOnProfileCallback(onProfileCallback: OnProfileCallback) {
        this.onProfileCallback = onProfileCallback
    }

    fun setUserData(userItem: ArrayList<UserProfiles>) {
        userList.clear()
        userList.addAll(userItem)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var names: TextView = itemView.findViewById(R.id.profile_name)
        var username: TextView = itemView.findViewById(R.id.profile_username)
        var userPict: ImageView = itemView.findViewById(R.id.profile_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val createView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ListViewHolder(createView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (names, username, pfp) = userList[position]
        holder.names.text = names
        holder.username.text = username
        Glide.with(holder.itemView.context)
            .load(pfp)
            .circleCrop()
            .into(holder.userPict)
        holder.itemView.setOnClickListener {
            onProfileCallback.onProfileClicked(userList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = userList.size
}