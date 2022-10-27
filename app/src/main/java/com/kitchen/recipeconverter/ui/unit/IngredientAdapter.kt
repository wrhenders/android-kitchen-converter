package com.kitchen.recipeconverter.ui.unit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.Ingredient

class IngredientAdapter( private val ingredientList: List<Ingredient>,
                         private val onItemClicked: (Ingredient)->Unit) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private var selectedItemPosition: Int? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.ingredient_text_view)
        val cardView: LinearLayout = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = ingredientList[position].name
        holder.itemView.setOnClickListener {
            onItemClicked(ingredientList[holder.adapterPosition])
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