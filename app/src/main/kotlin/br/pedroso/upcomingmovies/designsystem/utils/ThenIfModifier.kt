package br.pedroso.upcomingmovies.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.thenIf(condition: Boolean, modify: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.modify()
    } else {
        this
    }
}
