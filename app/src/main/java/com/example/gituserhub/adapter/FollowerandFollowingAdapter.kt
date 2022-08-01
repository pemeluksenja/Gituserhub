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

class FollowerandFollowingAdapter() :
    RecyclerView.Adapter<FollowerandFollowingAdapter.ListViewHolder>() {
    private val followerList = ArrayList<UserProfiles>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var names: TextView = itemView.findViewById(R.id.profile_name)
        var username: TextView = itemView.findViewById(R.id.profile_username)
        var userPict: ImageView = itemView.findViewById(R.id.profile_image)
    }

    fun setUserData(userItem: ArrayList<UserProfiles>) {
        followerList.clear()
        followerList.addAll(userItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (names, username, pfp) = followerList[position]
        holder.names.text = names
        holder.username.text = username
        Glide.with(holder.itemView.context)
            .load(pfp)
            .circleCrop()
            .into(holder.userPict)
    }

    override fun getItemCount(): Int = followerList.size

}