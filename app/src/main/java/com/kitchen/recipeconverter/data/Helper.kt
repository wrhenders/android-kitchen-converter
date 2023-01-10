package com.kitchen.recipeconverter.data

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

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


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}