package com.recepie.compose_from_panel_side.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recepie.compose_from_panel_side.domain.model.Recipe
import com.recepie.compose_from_panel_side.presentation.ui.recipe.RecipeEvent.*
import com.recepie.compose_from_panel_side.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel
@Inject
constructor(
    private val recipeRepository: RecipeRepository,
    private val token: String
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {

                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null)
                            getRecipe(event.id)
                    }
                }

            } catch (e: Exception) {
                Log.e("onTriggerEvent", "Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true

        val recipe = recipeRepository.get(token = token, id = id)
        this.recipe.value = recipe

        loading.value = false
    }
}