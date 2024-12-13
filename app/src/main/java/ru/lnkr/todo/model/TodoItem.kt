package ru.lnkr.todo.model

import java.util.Date
import java.util.UUID

/**
 * Class for storage base data of task (todo item)
 * @property id - unique id of task
 * @property text - text of task
 * @property importance[Importance] - importance (priority) of task
 * @property deadline - deadline of task
 * @property isCompleted - is task completed or not
 * @property createdAt - date of creation
 * @property modifiedAt - date of modification. The completion of the task is also a modification
 */
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    var isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val modifiedAt: Date? = null
)

/**
 * Enum class for priority of task
 */
enum class Importance {
    NONE,
    LOW,
    HIGH
}