package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recepie.compose_from_panel_side.domain.model.Recipe
import com.recepie.compose_from_panel_side.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val recipeRepository: RecipeRepository,
    private val token: String
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }

                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e("RecipeListViewModel", "onTriggerEvent: Exception: ${e.message}")
            }
        }
    }

    // Use case #1
    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        val result = recipeRepository.search(
            token = token,
            page = 1,
            query = query.value
        )
        loading.value = false
        recipes.value = result
    }

    // Use case #2
    private suspend fun nextPage() {
        //Prevent duplicate event due to recompose happening to quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()

            if (page.value > 1) {
                val result = recipeRepository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                appendRecipes(result)
            }

            loading.value = false
        }
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(scrollPosition: Int) {
        categoryScrollPosition = scrollPosition
    }
}