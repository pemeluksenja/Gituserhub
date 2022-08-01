package com.example.gituserhub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituserhub.R
import com.example.gituserhub.UserDetail
import com.example.gituserhub.adapter.FollowerandFollowingAdapter
import com.example.gituserhub.databinding.FragmentFollowersBinding
import com.example.gituserhub.viewmodel.FollowersViewModel
import com.example.gituserhub.viewmodel.UserDetailViewModel

class FollowersFragment : Fragment() {
    private lateinit var bind: FragmentFollowersBinding
    private lateinit var followerandFollowingAdapter: FollowerandFollowingAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = FragmentFollowersBinding.bind(view)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)


        followersViewModel.getFollowerList(UserDetail.FOLLOWERS)
        followerandFollowingAdapter = FollowerandFollowingAdapter()
        showLoadingProcess(true)

        followersViewModel.getProfileFollowers().observe(viewLifecycleOwner) { items ->
            if  (items!= null){
                followerandFollowingAdapter.setUserData(items)
                showLoadingProcess(false)

                if (items.size != 0){
                    displayRecycle()
                    showNotFound(false)
                } else {
                    showLoadingProcess(false)
                    showNotFound(true)
                    Toast.makeText(requireContext(), "Pengguna ini tidak memiliki followers", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    private fun displayRecycle() {
        bind.followerRecyclerView.layoutManager = LinearLayoutManager(activity)
        bind.followerRecyclerView.setHasFixedSize(true)
        bind.followerRecyclerView.adapter = followerandFollowingAdapter
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    private fun showNotFound(isNotFound: Boolean){
        if (isNotFound){
            bind.followersNotFound.visibility = View.VISIBLE
        } else {
            bind.followersNotFound.visibility = View.GONE
        }
    }
}