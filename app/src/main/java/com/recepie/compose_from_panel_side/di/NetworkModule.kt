package com.recepie.compose_from_panel_side.di

import com.google.gson.GsonBuilder
import com.recepie.compose_from_panel_side.network.RecipeService
import com.recepie.compose_from_panel_side.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Provides
    fun providesRecipeService(): RecipeService {
        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)
    }

    @Provides
    fun providesAuthToken(): String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}