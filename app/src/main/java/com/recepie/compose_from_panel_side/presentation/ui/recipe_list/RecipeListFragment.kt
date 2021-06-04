package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepie.compose_from_panel_side.BaseApplication
import com.recepie.compose_from_panel_side.presentation.component.*
import com.recepie.compose_from_panel_side.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

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

                AppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    // State hosting
                    Column {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            newSearch = viewModel::newSearch,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            categoryScrollPosition = viewModel.categoryScrollPosition,
                            onToggleTheme = {
                                application.toggleLightTheme()
                            }
                        )

                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(color = MaterialTheme.colors.background)) {
                            if (loading) {
                                ShimmerRecipeCardItem(
                                    imageHeight = 250.dp,
                                    padding = 8.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                                CircularIndeterminateProgressBar(isDisplayed = loading)
                            }
                        }
                    }

                }
            }
        }
    }
}