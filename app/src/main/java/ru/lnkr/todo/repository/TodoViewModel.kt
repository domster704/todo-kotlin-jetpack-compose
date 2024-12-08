package ru.lnkr.todo.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ru.lnkr.todo.model.TodoItem

open class TodoViewModel : ViewModel() {
    private val _items = mutableStateListOf<TodoItem>()
    var isVisible: Boolean = false

    val items: List<TodoItem>
        get() =
            when (this.isVisible) {
                false -> _items.filter { !it.isCompleted }
                true -> _items
            }

    fun saveItem(item: TodoItem) {
        if (_items.any { it.id == item.id }) {
            _items.replaceAll { if (it.id == item.id) item else it }
        } else {
            _items.add(item)
        }
    }

    fun deleteItem(itemId: String) {
        _items.removeAll { it.id == itemId }
    }

    fun completeItem(itemId: String) {
        val item: TodoItem? = _items.find { it.id == itemId }
        if (item == null) {
            return
        }

        item.isCompleted = true
    }

    fun setVisibilityOfCompletedItems(isVisible: Boolean) {
        this.isVisible = isVisible
    }
}