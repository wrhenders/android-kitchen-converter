package com.kitchen.recipeconverter.data

import android.util.Log

class Converter {
    private val CUPS = "Cups"
    private val TEASPOONS = "Teaspoons"
    private val GRAMS = "Grams"
    private val OUNCES = "Ounces"
    private val TABLESPOONS = "Tablespoons"

    private val fractions = arrayOf(
        "\u00B9\u2075/\u2081\u2086",  // 15/16
        "\u215E",                     // 7/8
        "\u00B9\u00B3/\u2081\u2086",  // 13/16
        "\u00BE",                     // 3/4
        "\u00B9\u00B9/\u2081\u2086",  // 11/16
        "\u215D",                     // 5/8
        "\u2079/\u2081\u2086",        // 9/16
        "\u00BD",                     // 1/2
        "\u2077/\u2081\u2086",        // 7/16
        "\u215C",                     // 3/8
        "\u2075/\u2081\u2086",        // 5/16
        "\u00BC",                     // 1/4
        "\u00B3/\u2081\u2086",        // 3/16
        "\u215B",                     // 1/8
        "\u00B9/\u2081\u2086",        // 1/16
    )

    fun convertToDecimal(fractionString: String) : Double {
        when (fractionString) {
            "\u00B9\u2075/\u2081\u2086" -> return 15/16.0
            "\u215E" -> return 7/8.0
            "\u00B9\u00B3/\u2081\u2086" -> return 13/16.0
            "3/4", "\u00BE" -> return 3/4.0
            "\u00B9\u00B9/\u2081\u2086" -> return 11/16.0
            "\u215D" -> return 5/8.0
            "\u2079/\u2081\u2086" -> return 9/16.0
            "1/2", "\u00BD" -> return 1/2.0
            "\u2077/\u2081\u2086" -> return 7/16.0
            "3/8","\u215C" -> return 3/8.0
            "\u2075/\u2081\u2086" -> return 5/16.0
            "1/4", "\u00BC" -> return 1/4.0
            "\u00B3/\u2081\u2086" -> return 3/16.0
            "1/8", "\u215B" -> return 1/8.0
            "\u00B9/\u2081\u2086" -> return 1/16.0
            "1/3","\u2153" -> return 1/3.0
            "2/3", "\u2154" -> return 2/3.0
        }
        return fractionString.toDoubleOrNull() ?: 0.0
    }

    fun getUnitType(shortUnitType:String?):String{
        when (shortUnitType?.lowercase()) {
            "g", "gram", "grams" -> return GRAMS
            "t", "tsp", "teaspoon", "teaspoons" -> return TEASPOONS
            "T", "tbsp","tablespoon","tablespoons" -> return TABLESPOONS
            "oz", "ounce", "ounces" -> return OUNCES
            "c", "cup", "cups" -> return CUPS
        }
        return ""
    }

    fun getGramItUnit(unitString:String?):String {
        when(getUnitType(unitString)) {
            GRAMS -> return "g"
            TEASPOONS -> return "t"
            TABLESPOONS -> return "T"
            OUNCES -> return "oz"
            CUPS -> return "c"
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