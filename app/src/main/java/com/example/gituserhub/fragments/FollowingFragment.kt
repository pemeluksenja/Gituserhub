package com.example.gituserhub.fragments

import android.os.Bundle
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
import com.example.gituserhub.databinding.FragmentFollowingBinding
import com.example.gituserhub.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private lateinit var bind: FragmentFollowingBinding
    private lateinit var followersAndFollowingAdapter: FollowerandFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = FragmentFollowingBinding.bind(view)
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.getFollowingList(UserDetail.FOLLOWING)
        followersAndFollowingAdapter = FollowerandFollowingAdapter()
        showLoadingProcess(true)
        followingViewModel.getProfileFollowing().observe(viewLifecycleOwner) { items ->
            if (items !== null) {
                followersAndFollowingAdapter.setUserData(items)
                showLoadingProcess(false)
            }
        }
        if (UserDetail.FOLLOWINGCOUNT!== 0){
            displayRecycle()
            showNotFound(false)


        } else {
            showLoadingProcess(false)
            showNotFound(true)
            Toast.makeText(requireContext(), "Pengguna ini tidak memiliki following", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayRecycle() {
        bind.followingRecyclerView.layoutManager = LinearLayoutManager(activity)
        bind.followingRecyclerView.adapter = followersAndFollowingAdapter
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    private fun showNotFound(isNotFound: Boolean) {
        if (isNotFound) {
            bind.notFound.visibility = View.VISIBLE
        } else {
            bind.notFound.visibility = View.GONE
        }
    }
}