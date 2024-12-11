package ru.lnkr.todo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.ui.swipe.DismissBackground

const val TIMEOUT = 100L

enum class TodoItemSwipeState {
    None,
    Completed,
    Deleted,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDoItem(
    item: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    onSwipeFinished: (item: TodoItem) -> Unit,
) {
    val itemState = remember { mutableStateOf(TodoItemSwipeState.None) }
    val viewModel = VMCompositionLocal.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    itemState.value = TodoItemSwipeState.Completed
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    itemState.value = TodoItemSwipeState.Deleted
                }

                SwipeToDismissBoxValue.Settled -> {
                    itemState.value = TodoItemSwipeState.None
                    return@rememberSwipeToDismissBoxState false
                }
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .51f }
    )

    LaunchedEffect(itemState.value) {
        when (itemState.value) {
            TodoItemSwipeState.Completed -> {
                kotlinx.coroutines.delay(TIMEOUT)
                viewModel.completeItem(item.id)
                onSwipeFinished(item)
            }

            TodoItemSwipeState.Deleted -> {
                kotlinx.coroutines.delay(TIMEOUT)
                viewModel.deleteItem(item.id)
                onSwipeFinished(item)
            }

            else -> {
            }
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        backgroundContent = { DismissBackground(dismissState) },
    ) {
        AnimatedVisibility(
            visible = itemState.value == TodoItemSwipeState.None,
            enter = EnterTransition.None,
            exit = slideOutHorizontally(
                targetOffsetX = {
                    when (itemState.value) {
                        // Движение вправо
                        TodoItemSwipeState.Completed -> it / 2
                        // Движение влево
                        TodoItemSwipeState.Deleted -> (-it) / 2
                        else -> it / 2
                    }
                },
            )
        ) {
            TodoItemCard(item = item) { onItemClick(item) }
        }
    }
}