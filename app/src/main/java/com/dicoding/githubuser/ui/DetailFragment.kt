package com.dicoding.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.dicoding.githubuser.viewModel.FolloViewModel
import com.dicoding.githubuser.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private val FolloViewModel : FolloViewModel by viewModels<FolloViewModel>()
    private lateinit var binding: FragmentDetailBinding

    private var position: Int = 0
    private var username: String? = null

    companion object {
        const val POSITION = "position"
        const val USERNAME = "username"
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        binding = FragmentDetailBinding.inflate(inflater,container,false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        arguments?.let {
            position = it.getInt(POSITION)
            username = it.getString(USERNAME)
        }
        FolloViewModel.isLoading.observe(viewLifecycleOwner) {
            isLoading -> showLoading(isLoading)
        }

        FolloViewModel._dataFollo.observe(viewLifecycleOwner){_dataFollo ->
            setFollo(_dataFollo)
    }

        if (position == 1) {
            FolloViewModel.getFollowers(username?:"")
        } else {
            FolloViewModel.getFollowing(username?:"")
        }
        return binding.root
    }


    private fun setFollo(user: List<ItemsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(user)
        binding.rvFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
