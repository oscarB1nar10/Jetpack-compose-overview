package com.recepie.compose_from_panel_side.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOff
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.recepie.compose_from_panel_side.BaseApplication
import com.recepie.compose_from_panel_side.presentation.component.*
import com.recepie.compose_from_panel_side.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
                val isShowing = remember { mutableStateOf(false) }
                val snackbarHostState = remember{SnackbarHostState()}

                Column() {
                    Button(onClick = {
                        lifecycleScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Hey look a snack bar",
                                actionLabel = "Hide",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Text(text = "Show Snackbar")
                    }

                    DecoupledSnackBarDemo(snackbarHostState = snackbarHostState)
                }

                /*AppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    Scaffold(
                        topBar = {
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
                        },
                        bottomBar = {
                            MyBottomBar()
                        },
                        drawerContent = {
                            MyDrawer()
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.surface)
                        ) {
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

                }*/
            }
        }
    }
}


//TODO('Pass navigation controller as argument to navigate to the different destinations')
@Composable
fun MyBottomBar() {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.BookmarkRemove, contentDescription = "A random item") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.BlurOff, contentDescription = "A random item") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Book, contentDescription = "A random item") }
        )
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text(text = "Item1")
        Text(text = "Item2")
        Text(text = "Item3")
        Text(text = "Item4")
    }
}

@Composable
fun DecoupledSnackBarDemo(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val snackBar = createRef()

        SnackbarHost(
            modifier = Modifier.constrainAs(snackBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(text = snackbarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )
    }
}

@Composable
fun SnackBarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {

    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val snackBar = createRef()

            Snackbar(
                modifier = Modifier.constrainAs(snackBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(
                            onClick = onHideSnackbar,
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {
                Text(text = "Hey look a snack bar")
            }
        }
    }

}

























