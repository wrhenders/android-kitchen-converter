package com.kitchen.recipeconverter.ui.gramit



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.doOnAttach
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.Converter
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.IngredientList
import com.kitchen.recipeconverter.data.changeStrokeColor

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
        val ingredientNames = IngredientList().getList().map{ing->ing.name}
    }

    override fun getItemId(position: Int): Long {
        return itemsList[position].id
    }

    override fun getItemViewType(position: Int): Int {
        return position
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
        holder.unitText.setText(itemsList[position].unit.toString())
        holder.ingredientText.setText(itemsList[position].ingredient.toString())
        Log.d("updating","bind to list: $itemsList")

        fun onClick() {
            val currentItem =
                GramItItem(holder.quantityText.text.toString(), holder.unitText.text.toString(), holder.ingredientText.text.toString())
            Log.d("updating", "click! $currentItem")
            onItemClicked(currentItem, position)
        }
         fun TextInputLayout.changeBoxColor(type: String) {
             setBoxStrokeColorStateList(changeStrokeColor(type))
         }


        fun updateQuantity() {
            val updatedQuantity = holder.quantityText.text.toString()
            if (updatedQuantity.toDoubleOrNull() is Double) {
                holder.quantityLayout.changeBoxColor("default")
                onClick()
            } else {
                holder.quantityLayout.changeBoxColor("error")
            }
        }

        fun updateUnit(){
            val updatedUnit = holder.unitText.text.toString()
            if (Converter().getUnitType(updatedUnit).isNotEmpty()) {
                holder.unitLayout.changeBoxColor("default")
                unitAdapter.filter.filter(null)
                onClick()
            } else {
                holder.unitLayout.changeBoxColor("error")
            }
        }
        fun updateIngredient(){
            Log.d("updating", "test")
            val updatedIngredient = holder.ingredientText.text.toString()
            if (updatedIngredient in holder.ingredientNames) {
                holder.ingredientLayout.changeBoxColor("default")
                ingredientAdapter.filter.filter(null)
                onClick()
            } else {
                holder.ingredientLayout.changeBoxColor("error")
            }
        }

        holder.quantityText.doOnAttach { updateQuantity() }
        holder.quantityText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) updateQuantity() }
        holder.quantityText.doAfterTextChanged { updateQuantity() }

        holder.unitText.doOnAttach { updateUnit() }
        holder.unitText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) updateUnit() }
        holder.unitText.doAfterTextChanged { updateUnit() }

        holder.ingredientText.doOnAttach { updateIngredient() }
        holder.ingredientText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) updateIngredient() }
        holder.ingredientText.doAfterTextChanged { updateIngredient() }
    }

    override fun getItemCount() = itemsList.size
}


