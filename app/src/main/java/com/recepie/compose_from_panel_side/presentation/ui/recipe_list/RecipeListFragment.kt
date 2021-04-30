package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepie.compose_from_panel_side.presentation.component.FoodCategoryChip
import com.recepie.compose_from_panel_side.presentation.component.RecipeCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


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

                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colors.primary,
                        elevation = 8.dp,
                    ) {

                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = query,
                                    onValueChange = { newQuery ->
                                        viewModel.onQueryChanged(newQuery)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .padding(8.dp),
                                    label = {
                                        Text(text = "Search")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = "Search icon"
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewModel.newSearch()
                                        }
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = MaterialTheme.colors.onSurface,
                                        backgroundColor = Color.White,
                                    )
                                )
                            }

                            val listState = rememberLazyListState()
                            // Remember a CoroutineScope to be able to launch
                            val coroutineScope = rememberCoroutineScope()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, bottom = 8.dp),
                                state = listState
                            ) {
                                getAllCategories().forEachIndexed { index, foodCategory ->
                                    item {
                                        FoodCategoryChip(
                                            category = foodCategory.value,
                                            isSelected = selectedCategory == foodCategory,
                                            onSelectedCategoryChanged = {
                                                viewModel.onSelectedCategoryChanged(it)
                                                viewModel.onChangeCategoryScrollPosition(index)
                                            },
                                            onExecuteSearch = viewModel::newSearch
                                        )
                                    }
                                }
                            }
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = viewModel.categoryScrollPosition)
                            }

                        }
                    }

                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }
}