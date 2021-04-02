package com.recepie.compose_from_panel_side.repository

import com.recepie.compose_from_panel_side.domain.model.Recipe
import com.recepie.compose_from_panel_side.network.RecipeService
import com.recepie.compose_from_panel_side.network.model.RecipeDtoMapper

class RecipeRepository_Impl constructor(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(
            recipeService.search(
                token = token,
                page = page,
                query = query
            ).recipes
        )
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token = token, id = id))
    }
}