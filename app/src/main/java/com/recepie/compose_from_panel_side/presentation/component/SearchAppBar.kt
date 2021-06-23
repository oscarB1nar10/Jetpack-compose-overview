package com.recepie.compose_from_panel_side.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.recepie.compose_from_panel_side.presentation.ui.recipe_list.FoodCategory
import com.recepie.compose_from_panel_side.presentation.ui.recipe_list.getAllCategories
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    categoryScrollPosition: Int,
    onToggleTheme: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = query,
                    onValueChange = { newQuery ->
                        onQueryChanged(newQuery)
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
                            contentDescription = "Search icon",
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onSurface,
                        backgroundColor = MaterialTheme.colors.secondary,
                    ),
                    textStyle = MaterialTheme.typography.button

                )

                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "change theme mode"
                        )
                    }

                }
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
                                onSelectedCategoryChanged(it)
                                onChangeCategoryScrollPosition(index)
                            },
                            onExecuteSearch = {onExecuteSearch()}
                        )
                    }
                }
            }
            coroutineScope.launch {
                listState.animateScrollToItem(index = categoryScrollPosition)
            }

        }
    }
}