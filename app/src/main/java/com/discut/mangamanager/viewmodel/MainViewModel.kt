package com.discut.mangamanager.viewmodel

import com.discut.mangamanager.contract.MainEffect
import com.discut.mangamanager.contract.MainEvent
import com.discut.mangamanager.contract.MainState
import com.discut.mangamanager.domain.navigation.NavigationBarItems
import com.discut.mvi.BaseViewModel

internal class MainViewModel : BaseViewModel<MainState, MainEvent, MainEffect>() {
    override fun initialState(): MainState {
        return MainState(
            navBarItems = listOf(
                NavigationBarItems.Explore,
                NavigationBarItems.Download
            )
        )
    }

    override suspend fun handleEvent(event: MainEvent, state: MainState): MainState {
        return when (event) {
            is MainEvent.ClickNavigationItem -> {
                sendEffect(MainEffect.NavigateTo(event.navigationItem.route))
                state
            }
        }
    }

}