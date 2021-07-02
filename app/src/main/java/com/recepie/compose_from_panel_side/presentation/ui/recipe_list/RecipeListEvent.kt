package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

sealed class RecipeListEvent {

    // We can replace [object with class] but due those classes
    // are not taking any argument [object] notation is fine.
    object  NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()
}