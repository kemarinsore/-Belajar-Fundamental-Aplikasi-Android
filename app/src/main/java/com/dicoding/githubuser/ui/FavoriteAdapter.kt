package com.dicoding.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.databinding.ItemReviewBinding
import com.dicoding.githubuser.viewModel.FavoriteViewModel

class FavoriteAdapter(private val onFavoriteClick: (Favorite) -> Unit):
    ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = getItem(position)
        val username = item.username

        holder.bind(item)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailUser::class.java).apply {
                this.putExtra(DetailUser.DETAIL, username)
            }
            holder.run{
                username.also{
                    if (it != null) {
                        SectionPagerAdapter.username = it
                    }
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    class FavoriteViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite){
            binding.username.text = item.username
            Glide.with(binding.root.context)
                .load(item.avatarUrl)
                .into(binding.imageProfil)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }



}