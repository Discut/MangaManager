package com.discut.mangamanager.ui.screen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.discut.mangamanager.contract.MainEffect
import com.discut.mangamanager.contract.MainEvent
import com.discut.mangamanager.contract.MainState
import com.discut.mangamanager.domain.navigation.NavigationBarItems
import com.discut.mangamanager.ui.component.CustomEdit
import com.discut.mangamanager.viewmodel.MainViewModel
import com.discut.mvi.CollectSideEffect


@Composable
internal fun MainScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    viewModel.CollectSideEffect {
        when (it) {
            is MainEffect.NavigateTo -> navController.navigate(it.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            is MainEffect.OpenAbout -> TODO()
        }
    }


    HomeScreenContent(state,
        navBarIsSelected = { item -> navController.currentBackStackEntryAsState().value?.destination?.route == item.route },
        navBarClick = {
            viewModel.sendEvent(MainEvent.ClickNavigationItem(it))
        },
        searchBarClick = {

        })
    {
        HomeNavGraph(
            defaultPage = state.defaultPage,
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

/**
 * 主屏内容导航
 */
@Composable
private fun HomeNavGraph(
    modifier: Modifier = Modifier,
    defaultPage: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = defaultPage,
        modifier = modifier
    ) {
        composable(NavigationBarItems.Explore.route) {
            /*TodoScreen()*/
        }
        composable(NavigationBarItems.Download.route) {
            /*AboutScreen()*/
        }
    }
}


/**
 * 主屏内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: MainState,
    navBarIsSelected: @Composable (item: NavigationBarItems) -> Boolean,
    navBarClick: (item: NavigationBarItems) -> Unit,
    searchBarClick: () -> Unit,
    content: @Composable (padding: PaddingValues) -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CustomEdit(
                text = searchText,
                onValueChange = {
                    searchText = it
                },
                startIcon = Icons.Filled.Search,
                hint = "More...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 10.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xBCE9E9E9))
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            awaitPointerEvent(PointerEventPass.Main)
                            searchBarClick()
                            /*navControllerTop.navigate("about") {
                                popUpTo(navControllerTop.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }*/
                        }
                    }
                    .clickable { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        },
        bottomBar = {
            NavigationBar(modifier = Modifier.wrapContentHeight()) {
                state.navBarItems.forEach {
                    NavigationBarItem(
                        icon = { Icon(it.icon.asPainterResource(), contentDescription = null) },
                        label = { Text(text = it.title) },
                        selected = navBarIsSelected(it),
                        onClick = { navBarClick(it) }
                    )
                }
            }
        }) {
        content(it)
        BackHandler {
            context.startActivity(Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}
