package ru.lnkr.todo.ui.swipe

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.ui.TodoItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDoItem(
    item: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    onSwipeFinished: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val viewModel = VMCompositionLocal.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    viewModel.completeItem(item.id)
                    onSwipeFinished()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    viewModel.deleteItem(item.id)
                    onSwipeFinished()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

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