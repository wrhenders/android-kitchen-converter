package com.kitchen.recipeconverter.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R

class IngredientAdapter( private val ingredientList: List<String>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = 0

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.ingredient_text_view)
        val cardView: LinearLayout = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = ingredientList[position]
        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        if(selectedItemPosition == position) {
            holder.cardView.setBackgroundColor(Color.parseColor("grey"))
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("white"))
        }
    }

    override fun getItemCount() = ingredientList.size
}