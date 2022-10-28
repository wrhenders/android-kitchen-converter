package com.kitchen.recipeconverter.ui.gramit


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.Ingredient

class GramItAdapter(private val itemsList: List<GramItItem>,
                    private val unitAdapter: ArrayAdapter<String>,
                    private val ingredientAdapter: ArrayAdapter<String>,
                    private val onItemClicked: (GramItItem, Int)->Unit) :
    RecyclerView.Adapter<GramItAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quantityText: TextInputEditText = view.findViewById(R.id.quantity_text)
        val unitText: AutoCompleteTextView = view.findViewById(R.id.unit_text)
        val ingredientText: AutoCompleteTextView = view.findViewById(R.id.ingredient_text)
        var currentQuantity = quantityText.toString()
        var currentUnit = unitText.toString()
        var currentIngredient = ingredientText.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.gramit_ingredient, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Add the adapters for dropdown and autocomplete menus
        holder.unitText.setAdapter(unitAdapter)
        holder.ingredientText.setAdapter(ingredientAdapter)

        // Set the fields to correct current state
        holder.quantityText.setText(itemsList[position].quantity.toString())
        holder.unitText.setText(itemsList[position].unit)
        holder.ingredientText.setText(itemsList[position].ingredient)

        holder.quantityText.doAfterTextChanged {
            holder.currentQuantity = it.toString()
            onClick(holder.currentQuantity, holder.currentUnit, holder.currentIngredient, position)
        }
        holder.unitText.doAfterTextChanged {
            holder.currentUnit = it.toString()
            onClick(holder.currentQuantity, holder.currentUnit, holder.currentIngredient, position)
        }
        holder.ingredientText.doAfterTextChanged {
            holder.currentIngredient = it.toString()
            onClick(holder.currentQuantity, holder.currentUnit, holder.currentIngredient, position)
        }

    }

    private fun onClick(quantity: String, unit: String, ingredient: String, position: Int){
        val currentItem = GramItItem(quantity, unit, ingredient)
        onItemClicked(currentItem, position)
    }

    override fun getItemCount() = itemsList.size
}