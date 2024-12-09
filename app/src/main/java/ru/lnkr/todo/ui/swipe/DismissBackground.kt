package ru.lnkr.todo.ui.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.ui.theme.AppTheme

enum class SwipeStatus {
    Complete,
    Delete,
    None
}

data class SwipeData(
    val status: SwipeStatus,
    val color: Color,
    val icon: @Composable (() -> Unit)? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val swipeData = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> SwipeData(
            SwipeStatus.Complete,
            AppTheme.colors.colorGreen
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Complete",
                tint = Color.White
            )
        }

        SwipeToDismissBoxValue.EndToStart -> SwipeData(
            SwipeStatus.Delete,
            AppTheme.colors.colorRed
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Complete",
                tint = Color.White
            )
        }

        SwipeToDismissBoxValue.Settled -> SwipeData(
            SwipeStatus.None,
            Color.Transparent,
            null
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(swipeData.color)
            .padding(12.dp, 8.dp)
            .clip(RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (swipeData.status == SwipeStatus.Complete) {
            swipeData.icon?.let { it() }
        } else {
            Spacer(modifier = Modifier)
            swipeData.icon?.let { it() }
        }
    }
}