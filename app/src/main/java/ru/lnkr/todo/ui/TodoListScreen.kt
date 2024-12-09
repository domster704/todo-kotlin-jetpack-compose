package ru.lnkr.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.R
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.theme.AppTheme

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
        },
        containerColor = AppTheme.colors.backPrimary,
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
                .padding(innerPadding)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.backSecondary,
                contentColor = AppTheme.colors.labelPrimary
            ),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    ),
//                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(itemsList) { item ->
                    TodoItemCard(item = item) { onItemClick(item) }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Новое",
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                start = 36.dp
                            )
                            .clickable {
                                onAddClick()
                            },
                        color = AppTheme.colors.labelTertiary
                    )
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