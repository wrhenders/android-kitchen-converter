package com.kitchen.recipeconverter.ui.recipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.data.recipe.getFormattedDate
import com.kitchen.recipeconverter.databinding.RecipeItemBinding

class RecipeAdapter(private val onItemClicked: (Recipe)->Unit) :
    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback) {


    class RecipeViewHolder(private var binding: RecipeItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe){
            binding.recipeTitle.text = recipe.recipeTitle
            binding.dateModified.text = recipe.getFormattedDate()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }
}