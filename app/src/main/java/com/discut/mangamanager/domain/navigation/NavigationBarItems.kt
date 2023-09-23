package com.discut.mangamanager.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.discut.mangamanager.R

sealed class NavigationBarItems(
    val route: String,
    val title: String,
    val icon: NavigationBarIcon
) {
    object Explore :
        NavigationBarItems("explore", "探索", NavigationBarIcon.fromImageVector(Icons.Filled.Home))

    object Download : NavigationBarItems(
        "download",
        "下载",
        NavigationBarIcon.fromDrawableResource(R.drawable.baseline_download_24)
    )
}