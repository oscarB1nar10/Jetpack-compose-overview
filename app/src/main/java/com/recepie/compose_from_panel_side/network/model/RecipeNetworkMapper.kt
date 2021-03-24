package com.recepie.compose_from_panel_side.network.model

import com.recepie.compose_from_panel_side.domain.model.Recipe
import com.recepie.compose_from_panel_side.domain.util.EntityMapper

class RecipeNetworkMapper : EntityMapper<RecipeNetworkEntity, Recipe> {

    override fun mapFromEntity(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients,
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated
        )
    }

    fun fromNetworkEntityListToDomainList(recipeNetworkEntityList: List<RecipeNetworkEntity>): List<Recipe> {
        return recipeNetworkEntityList.map { mapFromEntity(it) }
    }

    fun fromDomainListToNetworkEntity(recipeList: List<Recipe>): List<RecipeNetworkEntity> {
        return recipeList.map { mapToEntity(it) }
    }
}