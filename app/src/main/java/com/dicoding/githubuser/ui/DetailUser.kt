package com.dicoding.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.viewModel.DetailViewModel
import com.dicoding.githubuser.viewModel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()


    companion object {
        var DETAIL = "username"
        private val TAB_TITLE = arrayOf("Following", "Followers")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(DETAIL)
        binding.tvusername.text = username

        username?.let { viewModel.getUserData(it) }
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }


        supportActionBar?.elevation = 0f

        supportActionBar?.apply {
            title = "Detail User"
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.detailUser.observe(this) { userDetail ->
            getUserDetail(userDetail)


            val sectionPagerAdapter = username?.let { SectionPagerAdapter(this, it) }
            if (sectionPagerAdapter != null) {
                val viewPager: ViewPager2 = binding.viewPager
                viewPager.adapter = sectionPagerAdapter
                val tabs: TabLayout = binding.tabs

                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = TAB_TITLE[position]
                }.attach()
            }
        }

        val _FavoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.detailUser.observe(this) { DetailUser ->
            getUserDetail(DetailUser)
            var favStatus = false
            DetailUser.login.let {
                if (it != null) {
                    _FavoriteViewModel.getFavoriteUsername(it).observe(this) { favUser ->
                        if (favUser != null && favUser.isfavorite) {
                            favStatus = true
                            binding.fabFav.setImageResource(R.drawable.fav_full)
                        } else {
                            favStatus = false
                            binding.fabFav.setImageResource(R.drawable.fav_null)
                        }
                    }
                }
            }
            binding.fabFav.setOnClickListener {
                val insert = Favorite(
                    id = DetailUser.id,
                    username = DetailUser.login,
                    avatarUrl = DetailUser.avatarUrl,
                    isfavorite = !favStatus
                )
                val delete = Favorite(
                    id = DetailUser.id,
                    username = DetailUser.login,
                    avatarUrl = DetailUser.avatarUrl,
                    isfavorite = favStatus
                )
                if (favStatus) {
                    _FavoriteViewModel.delete(delete)
                    Toast.makeText(this, "User Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                } else {
                    _FavoriteViewModel.insert(insert)
                    Toast.makeText(this, "User Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                }
                favStatus = !favStatus
            }
        }

    }

    fun getUserDetail(userDetail: DetailUserResponse) {
        binding.tvusername.text = userDetail.login
        binding.tvnama.text = userDetail.name.toString()


        Glide.with(binding.root.context)
            .load(userDetail.avatarUrl)
            .into(binding.imageProfil)

        binding.tvfollowing.text =
            StringBuilder(userDetail.following.toString()).append(" Following")
        binding.tvfollowers.text =
            StringBuilder(userDetail.followers.toString()).append(" Followers")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@DetailUser, MainActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                startActivity(Intent((this@DetailUser), MainActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

