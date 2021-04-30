package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import com.recepie.compose_from_panel_side.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory (val value: String) {

    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllCategories(): List<FoodCategory>{
    return listOf(CHICKEN, BEEF, SOUP, DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT)
}

fun getCategory(foodCategory: String): FoodCategory?{
    val foodCategorySelected = FoodCategory.values().filter { it.value == foodCategory}
    return foodCategorySelected.firstOrNull()
}