package ru.lnkr.todo.model

import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    var isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val modifiedAt: Date? = null
)

enum class Importance {
    LOW, NORMAL, URGENT
}