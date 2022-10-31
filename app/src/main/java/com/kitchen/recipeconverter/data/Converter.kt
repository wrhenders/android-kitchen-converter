package com.kitchen.recipeconverter.data

import android.util.Log

class Converter {
    private val CUPS = "Cups"
    private val TEASPOONS = "Teaspoons"
    private val GRAMS = "Grams"
    private val OUNCES = "Ounces"
    private val TABLESPOONS = "Tablespoons"

    fun getUnitType(shortUnitType:String?):String{
        when (shortUnitType) {
            "g" -> return GRAMS
            "t" -> return TEASPOONS
            "T" -> return TABLESPOONS
            "oz" -> return OUNCES
            "c" -> return CUPS
        }
        return ""
    }

    fun conversionType(convertFrom: String, convertTo: String, ingredient: Ingredient): Double {
        Log.d("Convert", "$convertFrom $convertTo")
        when (convertFrom) {
            CUPS -> {
                when (convertTo) {
                    GRAMS -> return ingredient.weightConversion
                    TEASPOONS -> return 48.0
                    TABLESPOONS -> return 16.0
                    CUPS -> return 1.0
                    OUNCES -> return 8.0
                }
            }
            TEASPOONS -> {
                when (convertTo) {
                    GRAMS -> return ingredient.weightConversion/48
                    CUPS -> return (1.0/48)
                    TABLESPOONS -> return (1.0/3)
                    TEASPOONS -> return 1.0
                    OUNCES -> return (1.0/6)
                }
            }
            TABLESPOONS -> {
                when (convertTo) {
                    GRAMS -> return ingredient.weightConversion/16
                    CUPS -> return (1.0/16)
                    TABLESPOONS -> return 1.0
                    TEASPOONS -> return 3.0
                    OUNCES -> return (1.0/2)
                }
            }
            OUNCES -> {
                when (convertTo) {
                    GRAMS -> return ingredient.weightConversion/8
                    CUPS -> return (1.0/8)
                    TABLESPOONS -> return 2.0
                    TEASPOONS -> return 6.0
                    OUNCES -> return 1.0
                }
            }
            GRAMS -> {
                when (convertTo) {
                    GRAMS -> return 1.0
                    CUPS -> return 1/ingredient.weightConversion
                    OUNCES -> return 1/(ingredient.weightConversion/8)
                    TABLESPOONS -> return 1/(ingredient.weightConversion/16)
                    TEASPOONS -> return 1/(ingredient.weightConversion/48)
                }
            }
        }
        return 0.0
    }
}