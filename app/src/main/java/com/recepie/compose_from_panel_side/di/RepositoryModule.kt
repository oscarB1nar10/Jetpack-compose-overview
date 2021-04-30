package com.recepie.compose_from_panel_side.di

import com.recepie.compose_from_panel_side.network.RecipeService
import com.recepie.compose_from_panel_side.network.model.RecipeDtoMapper
import com.recepie.compose_from_panel_side.repository.RecipeRepository
import com.recepie.compose_from_panel_side.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(
            recipeService = recipeService,
            mapper = recipeDtoMapper
        )
    }
}