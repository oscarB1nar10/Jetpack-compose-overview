package com.recepie.compose_from_panel_side.presentation.component

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.recepie.compose_from_panel_side.R
import com.recepie.compose_from_panel_side.domain.model.Recipe
import com.recepie.compose_from_panel_side.presentation.component.util.SnackBarController
import com.recepie.compose_from_panel_side.presentation.ui.recipe_list.PAGE_SIZE
import com.recepie.compose_from_panel_side.presentation.ui.recipe_list.RecipeListEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackBarController: SnackBarController,
    navController: NavController
) {

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            ShimmerRecipeCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)

                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(RecipeListEvent.NextPageEvent)
                    }

                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null) {
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)

                                navController.navigate(
                                    R.id.action_recipeListFragment_to_recipeFragment,
                                    bundle
                                )

                            } else {
                                snackBarController.getScope().launch {
                                    snackBarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "Recipe error",
                                        actionLabel = "Ok"
                                    )
                                }
                            }
                        })
                }
            }
        }
    }
}