package com.discut.mangamanager.contract

import com.discut.mangamanager.domain.navigation.NavigationBarItems
import com.discut.mvi.contract.UiEffect
import com.discut.mvi.contract.UiEvent
import com.discut.mvi.contract.UiState


internal data class MainState(
    val defaultPage: String = NavigationBarItems.Explore.route,
    val navBarItems: List<NavigationBarItems>,
) : UiState

internal sealed interface MainEvent : UiEvent {

    data class ClickNavigationItem(val navigationItem: NavigationBarItems) : MainEvent
}

internal sealed interface MainEffect : UiEffect {
    data class NavigateTo(val route: String) : MainEffect

    data class OpenAbout(val title: String) : MainEffect
}