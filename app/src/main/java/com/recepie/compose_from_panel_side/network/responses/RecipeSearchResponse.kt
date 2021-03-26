package com.recepie.compose_from_panel_side.network.responses

import com.google.gson.annotations.SerializedName
import com.recepie.compose_from_panel_side.network.model.RecipeNetworkEntity

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeNetworkEntity>
)