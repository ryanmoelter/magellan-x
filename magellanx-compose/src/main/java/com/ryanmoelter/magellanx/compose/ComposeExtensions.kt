package com.ryanmoelter.magellanx.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ryanmoelter.magellanx.core.Displayable
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleOwner
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Created
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Destroyed
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Resumed
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Shown
import kotlinx.coroutines.flow.map

@Composable
@Suppress("ktlint:standard:function-naming")
public fun Displayable<@Composable () -> Unit>.Content(modifier: Modifier = Modifier) {
  Box(modifier = modifier) {
    view!!()
  }
}

@Composable
@Suppress("ktlint:standard:function-naming")
public fun Displayable(
  displayable: Displayable<@Composable () -> Unit>,
  modifier: Modifier = Modifier,
): Unit = displayable.Content(modifier)

@Composable
@Suppress("ktlint:standard:function-naming")
public fun LifecycleOwner.WhenShown(Content: @Composable () -> Unit) {
  val isShownFlow =
    remember {
      currentStateFlow
        .map { lifecycleState ->
          when (lifecycleState) {
            Destroyed, Created -> false
            Shown, Resumed -> true
          }
        }
    }
  val isShown by isShownFlow.collectAsState(false)
  if (isShown) {
    Content()
  }
}
