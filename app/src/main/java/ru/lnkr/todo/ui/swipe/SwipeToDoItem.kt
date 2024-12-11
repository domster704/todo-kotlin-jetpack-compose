package ru.lnkr.todo.ui.swipe

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.ui.TodoItemCard
import java.util.UUID

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
    onSwipeFinished: () -> Unit,
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
                viewModel.completeItem(item.id)
                onSwipeFinished()
            }
            TodoItemSwipeState.Deleted -> {
                viewModel.deleteItem(item.id)
                onSwipeFinished()
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