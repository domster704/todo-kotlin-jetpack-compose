package ru.lnkr.todo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.repository.TodoViewModel
import ru.lnkr.todo.ui.theme.AppTheme

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
    var importance by remember { mutableStateOf(item?.importance ?: Importance.NORMAL) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editing") },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Column {
                TextField(
                    value = itemText,
                    onValueChange = { itemText = it },
                    label = { Text("Task Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Importance.entries.forEach { importanceOption ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = importance == importanceOption,
                                onClick = { importance = importanceOption }
                            )
                            Text(importanceOption.name)
                        }
                    }
                }

                Button(onClick = {
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
                }) {
                    Text("Save")
                }
            }

            if (item != null) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            onDelete(item.id)
                            onClose()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            onComplete(item.id)
                            onClose()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Compete"
                        )
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