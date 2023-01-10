package com.kitchen.recipeconverter.data

import java.util.*

data class GramItItem(
    val quantity: String?,
    val unit: String?,
    val ingredient: String?,
    val id: Long = UUID.randomUUID().mostSignificantBits
) {
}