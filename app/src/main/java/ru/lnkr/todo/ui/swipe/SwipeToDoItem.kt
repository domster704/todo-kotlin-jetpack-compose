package ru.lnkr.todo.ui.swipe

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.TodoItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDoItem(
    item: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    onSwipeFinished: () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    TodoItemsRepository.completeItem(item.id)
                    onSwipeFinished()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    TodoItemsRepository.deleteItem(item.id)
                    onSwipeFinished()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier,
        backgroundContent = { DismissBackground(dismissState) },
    ) {
        TodoItemCard(item = item) { onItemClick(item) }
    }
}