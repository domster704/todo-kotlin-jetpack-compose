package ru.lnkr.todo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.ui.swipe.DismissBackground

// 100ms for 50% progress of swiping
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
    var itemState by remember { mutableStateOf(TodoItemSwipeState.None) }
    val viewModel = VMCompositionLocal.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (item.isCompleted) {
                        return@rememberSwipeToDismissBoxState false
                    }
                    itemState = TodoItemSwipeState.Completed
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    itemState = TodoItemSwipeState.Deleted
                }

                SwipeToDismissBoxValue.Settled -> {
                    itemState = TodoItemSwipeState.None
                    return@rememberSwipeToDismissBoxState false
                }
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .51f }
    )

    LaunchedEffect(itemState) {
        when (itemState) {
            TodoItemSwipeState.Completed -> {
                kotlinx.coroutines.delay((TIMEOUT / (dismissState.progress)).toLong())
                viewModel.completeItem(item.id)
                onSwipeFinished(item)
            }

            TodoItemSwipeState.Deleted -> {
                kotlinx.coroutines.delay((TIMEOUT / (dismissState.progress)).toLong())
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
        TodoItemCard(item = item) { onItemClick(item) }
    }
}