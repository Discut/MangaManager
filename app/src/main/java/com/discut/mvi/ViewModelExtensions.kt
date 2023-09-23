package com.discut.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.discut.mvi.contract.UiEffect
import com.discut.mvi.contract.UiEvent
import com.discut.mvi.contract.UiState

@Composable
fun <S : UiState, E : UiEvent, F : UiEffect> BaseViewModel<S, E, F>.CollectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: F) -> Unit),
) {
    val sideEffectFlow = this.uiEffect
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect { sideEffect(it) }
        }
    }
}