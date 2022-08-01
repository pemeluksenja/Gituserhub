package com.example.gituserhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituserhub.adapter.UserAdapter
import com.example.gituserhub.databinding.ActivityFavoriteUsersBinding
import com.example.gituserhub.helper.DetailViewModelFactory
import com.example.gituserhub.model.UserProfiles
import com.example.gituserhub.viewmodel.FavoriteViewModel

class FavoriteUsers : AppCompatActivity() {
    private lateinit var bind: ActivityFavoriteUsersBinding
    private lateinit var favUserViewModel: FavoriteViewModel
    private lateinit var favUserAdapter: UserAdapter
    private val list = ArrayList<UserProfiles>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.favRecycleView.setHasFixedSize(true)
        favUserAdapter = UserAdapter()
        favUserViewModel = getViewModel(this@FavoriteUsers)
        showLoading(true)
        val count = favUserViewModel.getTotalData()
        if (count > 0) {
            displayRecycleList()
        } else {
            emptyFavList(true)
            showLoading(false)
        }

        bind.refresh.setOnClickListener {
            val count = favUserViewModel.getTotalData()
            if (count > 0) {
                displayRecycleList()
            } else {
                emptyFavList(true)
                showLoading(false)
            }
        }

        bind.delete.setOnClickListener {
            favUserViewModel.deleteAll()
            list.clear()
            favUserAdapter.setUserData(list)
            favUserAdapter.notifyDataSetChanged()
            emptyFavList(true)
            Toast.makeText(
                this@FavoriteUsers,
                "Berhasil Menghapus Seluruh Data",
                Toast.LENGTH_SHORT
            ).show()
        }
        favUserViewModel.getFavUserList().observe(this) { item ->
            if (item != null) {
                Log.d("FavUserActivity", item.toString())
                for (user in item) {
                    val name = user.name?.uppercase()!!
                    val username = user.username!!
                    val ava = user.avatarUrl!!
                    val user = UserProfiles(
                        name,
                        username,
                        ava,
                        "Indonesia",
                        "PT Jaya Raya",
                        (0..100).random(),
                        (0..100).random(),
                        (0..100).random(),
                        true
                    )
                    showLoading(false)
                    list.add(user)
                }
                favUserAdapter.setUserData(list)
            }
        }
    }

    private fun getViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val favUserFactory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, favUserFactory).get(FavoriteViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    private fun displayRecycleList() {
        bind.favRecycleView.layoutManager = LinearLayoutManager(this@FavoriteUsers)
        bind.favRecycleView.setHasFixedSize(true)
        bind.favRecycleView.adapter = favUserAdapter
        emptyFavList(false)
        favUserAdapter.setOnProfileCallback(object : UserAdapter.OnProfileCallback {
            override fun onProfileClicked(data: UserProfiles) {
                chosenProfile(data)
                list.clear()
            }
        })
    }

    private fun chosenProfile(profiles: UserProfiles) {
        val sendData = Intent(this, UserDetail::class.java)
        sendData.putExtra(UserDetail.EXTRA_PROFILE, profiles)
        startActivity(sendData)
        Toast.makeText(this, "Memuat profile " + profiles.names, Toast.LENGTH_SHORT).show()
    }

    private fun emptyFavList(isEmpty: Boolean) {
        if (isEmpty) {
            bind.empty.visibility = View.VISIBLE
        } else {
            bind.empty.visibility = View.GONE
        }
    }
}