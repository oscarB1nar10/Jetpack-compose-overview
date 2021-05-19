package com.recepie.compose_from_panel_side.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.recepie.compose_from_panel_side.R
import com.recepie.compose_from_panel_side.presentation.component.HeartButtonState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import loadPicture


@ExperimentalCoroutinesApi
@Composable
fun AnimatedHeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartButtonState>,
    onToggle: () -> Unit
) {

    val idleIconSize = 24.dp
    val expandedIconSize = 80.dp

    val heartSize = animateDpAsState(
        targetValue = 100.dp,
        animationSpec = keyframes {
            durationMillis = 5000
            idleIconSize at 100
            expandedIconSize at 2000
        }
    )

    if (buttonState.value == ACTIVE) {
        loadPicture(drawable = R.drawable.heart_red).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Pulse heart",
                modifier = modifier
                    .clickable(onClick = onToggle)
                    .width(heartSize.value)
                    .height(heartSize.value)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = LinearOutSlowInEasing
                        )
                    )
            )
        }
    } else {
        loadPicture(drawable = R.drawable.heart_grey).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Pulse heart",
                modifier = modifier
                    .clickable(onClick = onToggle)
                    .width(idleIconSize)
                    .height(idleIconSize)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = LinearOutSlowInEasing
                        )
                    )
            )
        }
    }
}

enum class HeartButtonState {
    IDLE, ACTIVE
}
