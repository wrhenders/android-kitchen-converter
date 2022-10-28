package com.kitchen.recipeconverter.ui.gramit


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.GramItItem

class GramItAdapter(private val itemsList: List<GramItItem>,
                    private val unitAdapter: ArrayAdapter<String>,
                    private val ingredientAdapter: ArrayAdapter<String>) :
    RecyclerView.Adapter<GramItAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quantityText: TextInputEditText = view.findViewById(R.id.quantity_text)
        val unitText: AutoCompleteTextView = view.findViewById(R.id.unit_text)
        val ingredientText: AutoCompleteTextView = view.findViewById(R.id.ingredient_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.gramit_ingredient, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.unitText.setAdapter(unitAdapter)
        holder.ingredientText.setAdapter(ingredientAdapter)
        holder.quantityText.setText(itemsList[position].quantity.toString())
        holder.unitText.setText(itemsList[position].unit)
        holder.ingredientText.setText(itemsList[position].ingredient)
    }

    override fun getItemCount() = itemsList.size
}