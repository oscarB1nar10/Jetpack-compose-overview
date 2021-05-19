package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepie.compose_from_panel_side.presentation.component.AnimatedHeartButton
import com.recepie.compose_from_panel_side.presentation.component.HeartButtonState
import com.recepie.compose_from_panel_side.presentation.component.PulsingDemo
import com.recepie.compose_from_panel_side.presentation.component.SearchAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {


    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Viewmodel: $viewModel")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        newSearch = viewModel::newSearch,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                        categoryScrollPosition = viewModel.categoryScrollPosition
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val state = remember { mutableStateOf(HeartButtonState.IDLE) }

                        AnimatedHeartButton(
                            modifier = Modifier,
                            buttonState = state,
                            onToggle = {
                                state.value = if (state.value == HeartButtonState.IDLE)
                                    HeartButtonState.ACTIVE
                                else
                                    HeartButtonState.IDLE
                            }
                        )

                    }

                    //PulsingDemo()
                    /*Box(modifier = Modifier.fillMaxSize()) {
                        CircularIndeterminateProgressBar(isDisplayed = loading)
                        LazyColumn {
                            itemsIndexed(
                                items = recipes
                            ) { index, recipe ->
                                RecipeCard(recipe = recipe, onClick = {})
                            }
                        }
                    }*/
                }
            }
        }
    }
}