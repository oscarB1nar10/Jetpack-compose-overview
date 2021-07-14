package com.recepie.compose_from_panel_side.presentation.ui.recipe

sealed class RecipeEvent {

    data class GetRecipeEvent(
        val id: Int
    ) : RecipeEvent()
}