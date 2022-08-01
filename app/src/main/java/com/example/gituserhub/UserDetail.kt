package com.example.gituserhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.gituserhub.adapter.TabSectionsAdapter
import com.example.gituserhub.databinding.ActivityUserDetailBinding
import com.example.gituserhub.helper.DetailViewModelFactory
import com.example.gituserhub.model.UserProfiles
import com.example.gituserhub.roomdb.FavUser
import com.example.gituserhub.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetail : AppCompatActivity() {
    private lateinit var bind: ActivityUserDetailBinding
    private lateinit var userDetailViewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val users = intent.getParcelableExtra<UserProfiles>(EXTRA_PROFILE) as UserProfiles
        FOLLOWERS = users.usernames
        FOLLOWING = FOLLOWERS

        userDetailViewModel = getViewModel(this)
        userDetailViewModel.getUserProfile(users.usernames)
        userDetailViewModel.getUsers().observe(this) { items ->
            if (items !== null) {
                bindUserDetail(items)
                supportActionBar?.title = items.names
                add(items)
                FOLLOWINGCOUNT = items.following
            }
        }

        val tabSectionsAdapter = TabSectionsAdapter(this)
        val viewPagerTwo: ViewPager2 = findViewById(R.id.viewPagerTab)
        viewPagerTwo.adapter = tabSectionsAdapter
        val tabLayouts: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayouts, viewPagerTwo) { tab, position ->
            tab.text = resources.getString(TAB_SECTIONS[position])
        }.attach()
       isAlreadyFav()
    }

    private fun checkUser(username: String): Boolean{
        val check = userDetailViewModel.isUserAlreadyExist(username)
        if (check == 0){
            return false
        }
        return true
    }

    private fun add(items: UserProfiles){
        bind.fav.setOnClickListener {
            if (!checkUser(items.usernames)) {
                setUserToFav(items)
                bind.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(
                    this@UserDetail,
                    "Menambahkan ${items.usernames} ke Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                bind.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                deleteUserFromFavByUsn(items.usernames)
            }
        }
    }

    private fun isAlreadyFav() {
        if (!checkUser(FOLLOWING)) {
            bind.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        } else {
            bind.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun setUserToFav(items: UserProfiles) {
        val _id = intent.getIntExtra(EXTRA_ID, 0)
        val profileName = items.names
        val profileUsername = items.usernames
        val profileAva = items.pfp

        val fav = FavUser(
            _id, profileName, profileUsername, profileAva
        )
        userDetailViewModel.addUserToFav(fav)
    }

    private fun deleteUserFromFavByUsn(username: String){
        userDetailViewModel.deleteByUsername(username)
        Toast.makeText(this, "Berhasil menghapus ${username} dari Favorite", Toast.LENGTH_SHORT).show()
    }

    private fun bindUserDetail(items: UserProfiles) {
        bind.detailName.text = items.names
        bind.detailUsername.text = items.usernames
        Glide.with(this@UserDetail).load(items.pfp).into(bind.detailImage)
        bind.detailLocDummy.text = items.userLocs
        bind.detailCompanyDummy.text = items.company
        bind.detailFollowersDummy.text = items.followers.toString()
        bind.detailFollowingDummy.text = items.following.toString()
        bind.detailRepositoryDummy.text = items.repository.toString()
        if (!checkUser(items.usernames)) {
            bind.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        } else {
            bind.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun getViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val detailViewModelFactory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(
            activity,
            detailViewModelFactory
        ).get(UserDetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
        const val EXTRA_ID = "extra_ID"
        lateinit var FOLLOWERS: String
        lateinit var FOLLOWING: String
        var FOLLOWINGCOUNT = 0
        @StringRes
        private val TAB_SECTIONS = intArrayOf(
            R.string.profile_followers,
            R.string.profile_following
        )
    }
}