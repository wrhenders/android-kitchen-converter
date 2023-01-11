package com.kitchen.recipeconverter.ui.unit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.Ingredient
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.databinding.IngredientItemBinding

class IngredientAdapter( private val onItemClicked: (Ingredient)->Unit) :
    ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(DiffCallback) {
    private var selectedItem: Ingredient? = null

    class IngredientViewHolder(private var binding: IngredientItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = binding.cardView
        fun bind(ingredient: Ingredient) {
            binding.ingredientTextView.text = ingredient.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(IngredientItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
            selectedItem = current
            notifyDataSetChanged()
        }
        if(selectedItem == current) {
            holder.cardView.setBackgroundColor(Color.parseColor("grey"))
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("white"))
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }
        }

    }
}