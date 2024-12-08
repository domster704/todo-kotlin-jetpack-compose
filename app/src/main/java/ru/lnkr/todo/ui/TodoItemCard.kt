package ru.lnkr.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.theme.AppTheme

@Composable
fun TodoItemCard(item: TodoItem, onClick: () -> Unit) {
    var menuVisible by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<TodoItem?>(null) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        println("test123")
                        selectedItem = item
                        menuVisible = true
                    }
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (item.isCompleted) Color.Green else Color.Red)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
        }

        if (menuVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        TodoItemsRepository.deleteItem(item.id)
                    }) {
                        Text("Delete")
                    }

                    Button(onClick = {
                        TodoItemsRepository.completeItem(item.id)
                    }) {
                        Text("Complete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCardPreview() {
    AppTheme {
        TodoItemCard(TodoItemsRepository.getTodoItems()[0]) {}
    }
}