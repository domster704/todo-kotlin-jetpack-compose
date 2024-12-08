package ru.lnkr.todo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.lnkr.todo.R
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.theme.AppTheme

fun visibilityIconOnClick() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddClick: () -> Unit,
    onItemClick: (TodoItem) -> Unit,
) {
    var completedItemsVisibility by remember { mutableStateOf(false) }
    var itemsList by remember { mutableStateOf(TodoItemsRepository.getTodoItems()) }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(completedItemsVisibility) {
        itemsList = TodoItemsRepository.getTodoItems()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = topAppBarColors(
                    scrolledContainerColor = AppTheme.colors.backPrimary,
                    containerColor = AppTheme.colors.backPrimary,
                    titleContentColor = AppTheme.colors.labelPrimary,
                    actionIconContentColor = AppTheme.colors.colorBlue
                ),
                title = {
                    Text(
                        "Мои дела",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton({
                        completedItemsVisibility = !completedItemsVisibility
                        TodoItemsRepository.setVisibilityOfCompletedItems(completedItemsVisibility)
                    }) {
                        Icon(
                            painterResource(
                                if (completedItemsVisibility)
                                    R.drawable.visibility_icon
                                else
                                    R.drawable.visibility_off_icon
                            ),
                            contentDescription = "Add Task",
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick() },
                shape = CircleShape,
                containerColor = AppTheme.colors.colorBlue,
                contentColor = AppTheme.colors.colorWhite
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(itemsList) { item ->
                    TodoItemCard(item = item) { onItemClick(item) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    AppTheme {
        TodoListScreen({}, {})
    }
}