package com.kitchen.recipeconverter.data

import android.content.res.ColorStateList
import android.graphics.Color
import com.kitchen.recipeconverter.data.recipe.Recipe

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
            intArrayOf(errorColor, defaultColor, errorColor, errorColor)
        } else {
            intArrayOf(defaultColor, defaultColor, defaultColor, defaultColor)
        }
    return ColorStateList(states, colors)
}
