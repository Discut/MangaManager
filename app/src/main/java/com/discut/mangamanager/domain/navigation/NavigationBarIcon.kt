package com.discut.mangamanager.domain.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

class NavigationBarIcon(
    @DrawableRes private val resID: Int?,
    private val imageVector: ImageVector?
) {

    @Composable
    fun asPainterResource(): Painter {
        resID?.let {
            return painterResource(id = resID)
        }
        return rememberVectorPainter(image = imageVector!!)
    }

    companion object {
        fun fromDrawableResource(@DrawableRes resID: Int): NavigationBarIcon {
            return NavigationBarIcon(resID, null)
        }

        fun fromImageVector(imageVector: ImageVector?): NavigationBarIcon {
            return NavigationBarIcon(null, imageVector)
        }
    }
}