package com.recepie.compose_from_panel_side.repository

import com.recepie.compose_from_panel_side.domain.model.Recipe

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>
    suspend fun get(token: String, id: Int): Recipe
}