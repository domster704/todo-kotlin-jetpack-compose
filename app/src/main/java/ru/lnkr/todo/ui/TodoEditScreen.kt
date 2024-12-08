package ru.lnkr.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.theme.AppTheme

data class ImportanceViewData(
    val text: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    item: TodoItem?,
    onSave: (TodoItem) -> Unit,
    onClose: () -> Unit,
    onDelete: (Any?) -> Unit = {},
    onComplete: (Any?) -> Unit = {}
) {
    var itemText by remember { mutableStateOf(item?.text.orEmpty()) }
    var importance by remember { mutableStateOf(item?.importance ?: Importance.LOW) }

    var expanded by remember { mutableStateOf(false) }

    val importanceDictionary = mapOf(
        Importance.NONE to ImportanceViewData(
            "Нет",
            AppTheme.colors.labelPrimary
        ),
        Importance.LOW to ImportanceViewData(
            "Низкий",
            AppTheme.colors.labelPrimary
        ),
        Importance.HIGH to ImportanceViewData(
            "!! Высокий",
            AppTheme.colors.colorRed
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    scrolledContainerColor = AppTheme.colors.backPrimary,
                    containerColor = AppTheme.colors.backPrimary,
                    titleContentColor = AppTheme.colors.labelPrimary,
                    actionIconContentColor = AppTheme.colors.colorBlue
                ),
                title = {},
                actions = {
                    Text(
                        "СОХРАНИТЬ",
                        color = AppTheme.colors.colorBlue,
                        style = AppTheme.typography.button,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                onSave(
                                    when (item) {
                                        null -> TodoItem(
                                            text = itemText,
                                            importance = importance
                                        )

                                        else -> TodoItem(
                                            id = item.id,
                                            text = itemText,
                                            importance = importance
                                        )
                                    }
                                )
                                onClose()
                            }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                },
            )
        },
        containerColor = AppTheme.colors.backPrimary,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Column {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.backPrimary,
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = itemText,
                        onValueChange = { itemText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = AppTheme.typography.body,
                        minLines = 5,
                        placeholder = {
                            Text(
                                "Что надо сделать...",
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.labelTertiary
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = AppTheme.colors.labelPrimary,
                            unfocusedTextColor = AppTheme.colors.labelPrimary,

                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,

                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Важность",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.labelPrimary
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Importance.entries.forEach { importanceOption ->
                            DropdownMenuItem(
                                text = {
                                    importanceDictionary[importanceOption]?.let { Text(it.text) }
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = AppTheme.colors.labelPrimary,
                                ),
                                onClick = {
                                    importance = importanceOption
                                },
                            )
                            HorizontalDivider()
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoEditScreenPreview() {
    AppTheme {
        TodoEditScreen(TodoItemsRepository.getTodoItems()[0], {}, {})
    }
}