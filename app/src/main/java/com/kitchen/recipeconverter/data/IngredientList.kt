package com.kitchen.recipeconverter.data

class IngredientList {

    fun getList(): List<Ingredient> {
        return ingredientList.sortedBy { it.name }
    }

    private val ingredientList: List<Ingredient> =
        listOf(
            Ingredient("Flour, AP", 125.0),
            Ingredient("Sugar", 200.0),
            Ingredient("Water", 236.0),
            Ingredient("Oil, vegetable", 215.0),
            Ingredient("Oil, olive", 213.0),
            Ingredient("Almond Flour", 96.0),
            Ingredient("Almonds, whole", 142.0),
            Ingredient("Almonds, slivered", 114.0),
            Ingredient("Baking Powder", 192.0),
            Ingredient("Baking Soda", 288.0),
            Ingredient("Barley, pearled", 213.0),
            Ingredient("Breadcrumbs, dried", 112.0),
            Ingredient("Breadcrumbs, panko", 50.0),
            Ingredient("Sugar, Brown", 213.0),
            Ingredient("Butter", 227.0),
            Ingredient("Buttermilk", 227.0),
            Ingredient("Cashews", 113.0),
            Ingredient("Cheese, grated", 113.0),
            Ingredient("Cheese, ground Parmesan", 100.0),
            Ingredient("Cheese, Ricotta", 227.0),
            Ingredient("Chocolate, chunks", 140.0),
            Ingredient("Chocolate, chips", 170.0),
            Ingredient("Cocoa powder, unsweetened", 84.0),
            Ingredient("Coconut, shredded", 85.0),
            Ingredient("Coconut Oil", 224.0),
            Ingredient("Cornmeal", 138.0),
            Ingredient("Cornstarch", 112.0),
            Ingredient("Cream Cheese", 227.0),
            Ingredient("Jam", 340.0),
            Ingredient("Honey", 336.0),
            Ingredient("Dried Fruit", 142.0),
            Ingredient("Maple Syrup", 312.0),
            Ingredient("Milk", 227.0),
            Ingredient("Molasses", 340.0),
            Ingredient("Oats", 100.0),
            Ingredient("Peanut Butter", 270.0),
            Ingredient("Peanuts", 142.0),
            Ingredient("Pecans", 120.0),
            Ingredient("Pistachios, shelled", 120.0),
            Ingredient("Sugar, Powdered", 114.0),
            Ingredient("Raisins", 149.0),
            Ingredient("Sour Cream", 227.0),
            Ingredient("Cream", 225.0),
            Ingredient("Yogurt, greek", 285.0),
            Ingredient("Yogurt, regular", 245.0),
            Ingredient("Mayonnaise", 226.0),
            Ingredient("Vanilla Extract", 224.0),
            Ingredient("Salt, Diamond Crystal", 128.0),
            Ingredient("Salt, table", 288.0),
        )

}