package com.recepie.compose_from_panel_side.presentation.component

import DEFAULT_RECIPE_IMAGE
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.recepie.compose_from_panel_side.domain.model.Recipe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import loadPicture

const val IMAGE_HEIGHT = 260

@ExperimentalCoroutinesApi
@Composable
fun RecipeView(
    recipe: Recipe
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        recipe.featuredImage?.let { url ->
            val image = loadPicture(url, DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    "Recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                    val rank = recipe.rating.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )
                }
                recipe.publisher?.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                        text = if (updated != null) {
                            "Updated: $updated by $publisher"
                        } else {
                            "By $publisher"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }

                recipe.ingredients?.let { ingredients ->
                    for (ingredient in ingredients) {
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

    }

}