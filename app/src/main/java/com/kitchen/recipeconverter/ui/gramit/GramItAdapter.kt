package com.kitchen.recipeconverter.ui.gramit


import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.Converter
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.IngredientList

class GramItAdapter(private val itemsList: List<GramItItem>,
                    private val unitAdapter: ArrayAdapter<String>,
                    private val ingredientAdapter: ArrayAdapter<String>,
                    private val onItemClicked: (GramItItem, Int)->Unit) :
    RecyclerView.Adapter<GramItAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quantityText: TextInputEditText = view.findViewById(R.id.quantity_text)
        val unitText: AutoCompleteTextView = view.findViewById(R.id.unit_text)
        val ingredientText: AutoCompleteTextView = view.findViewById(R.id.ingredient_text)
        val quantityLayout: TextInputLayout = view.findViewById(R.id.quantity_text_label)
        val ingredientLayout: TextInputLayout = view.findViewById(R.id.ingredient_menu)
        val unitLayout: TextInputLayout = view.findViewById(R.id.unit_menu)
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
        holder.ingredientText.validator = IngredientValidator()

        // Set the fields to correct current state
        holder.quantityText.setText(itemsList[position].quantity)
        holder.unitText.setText(itemsList[position].unit)
        holder.ingredientText.setText(itemsList[position].ingredient)


        fun onClick() {
            val currentItem =
                GramItItem(holder.currentQuantity, holder.currentUnit, holder.currentIngredient)
            onItemClicked(currentItem, position)
        }

        fun changeStrokeColor(type: String): ColorStateList {
            val errorColor = Color.parseColor("#B00020")
            val defaultColor = Color.parseColor("#60000000")

            val states = arrayOf(
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_hovered),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf()
            )
            val colors =
                if (type == "error") {
                    intArrayOf(defaultColor, defaultColor, defaultColor, errorColor)
                } else {
                    intArrayOf(defaultColor, defaultColor, defaultColor, defaultColor)
                }
            return ColorStateList(states, colors)
        }

        holder.quantityText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val updatedQuantity = holder.quantityText.text.toString()
                if (updatedQuantity.toDoubleOrNull() is Double) {
                    holder.quantityLayout.setBoxStrokeColorStateList(changeStrokeColor("default"))
                    holder.currentQuantity = updatedQuantity
                    onClick()
                } else {
                    holder.quantityLayout.setBoxStrokeColorStateList(changeStrokeColor("error"))
                }
            }
        }

        holder.unitText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val updatedUnit = holder.unitText.text.toString()
                if (Converter().getUnitType(updatedUnit).isNotEmpty()) {
                    holder.unitLayout.setBoxStrokeColorStateList(changeStrokeColor("default"))
                    holder.currentUnit = updatedUnit
                    onClick()
                } else {
                    holder.unitLayout.setBoxStrokeColorStateList(changeStrokeColor("error"))
                }
            }
        }
        holder.ingredientText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val updatedIngredient = holder.ingredientText.text.toString()
                if (holder.ingredientText.validator.isValid(updatedIngredient)) {
                    holder.ingredientLayout.setBoxStrokeColorStateList(changeStrokeColor("default"))
                    holder.currentIngredient = updatedIngredient
                    onClick()
                } else {
                    holder.ingredientLayout.setBoxStrokeColorStateList(changeStrokeColor("error"))
                }
            }
        }
    }

    override fun getItemCount() = itemsList.size
}

class IngredientValidator : AutoCompleteTextView.Validator {
    override fun isValid(text: CharSequence?): Boolean {
        if(text.toString() in IngredientList().getList().map{ing->ing.name}){
            return true
        }
        return false
    }

    override fun fixText(invalidText: CharSequence?): CharSequence {
        return invalidText ?: ""
    }

}

