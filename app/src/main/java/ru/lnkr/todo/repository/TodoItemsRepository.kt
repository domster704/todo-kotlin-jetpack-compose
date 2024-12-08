package ru.lnkr.todo.repository

import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import java.util.Calendar

object TodoItemsRepository {
    private var viewModel = TodoViewModel()

    var completedItemsVisibility: Boolean = viewModel.isVisible
        get() = viewModel.isVisible
        private set

    init {
        viewModel.saveItem(TodoItem(id = "1", text = "Buy groceries", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 10)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 10)
        }.time))

        viewModel.saveItem(TodoItem(id = "2", text = "Complete homework", importance = Importance.URGENT, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.FEBRUARY, 14)
        }.time, isCompleted = true, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.FEBRUARY, 15)
        }.time))

        viewModel.saveItem(TodoItem(id = "3", text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged", importance = Importance.LOW, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.MARCH, 5)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.MARCH, 6)
        }.time))

        viewModel.saveItem(TodoItem(id = "4", text = "Prepare presentation", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.APRIL, 20)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.APRIL, 21)
        }.time))

        viewModel.saveItem(TodoItem(id = "5", text = "Go for a run", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.MAY, 30)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.MAY, 31)
        }.time))

        viewModel.saveItem(TodoItem(id = "6", text = "Buy a new phone", importance = Importance.URGENT, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.JUNE, 15)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.JUNE, 16)
        }.time))

        viewModel.saveItem(TodoItem(id = "7", text = "Finish reading book", importance = Importance.LOW, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.JULY, 8)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.JULY, 9)
        }.time))

        viewModel.saveItem(TodoItem(id = "8", text = "Fix broken chair", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.AUGUST, 25)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.AUGUST, 26)
        }.time))

        viewModel.saveItem(TodoItem(id = "9", text = "Submit assignment", importance = Importance.URGENT, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.SEPTEMBER, 10)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.SEPTEMBER, 11)
        }.time))

        viewModel.saveItem(TodoItem(id = "10", text = "Call the doctor", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.OCTOBER, 22)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.OCTOBER, 23)
        }.time))

        viewModel.saveItem(TodoItem(id = "11", text = "Clean garage", importance = Importance.LOW, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.NOVEMBER, 1)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.NOVEMBER, 2)
        }.time))

        viewModel.saveItem(TodoItem(id = "12", text = "Buy new shoes", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.DECEMBER, 5)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.DECEMBER, 6)
        }.time))

        viewModel.saveItem(TodoItem(id = "13", text = "Organize files", importance = Importance.LOW, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 3)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 4)
        }.time))

        viewModel.saveItem(TodoItem(id = "14", text = "Plan vacation", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.FEBRUARY, 25)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.FEBRUARY, 26)
        }.time))

        viewModel.saveItem(TodoItem(id = "15", text = "Update resume", importance = Importance.URGENT, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.MARCH, 18)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.MARCH, 19)
        }.time))

        viewModel.saveItem(TodoItem(id = "16", text = "Buy gift for friend", importance = Importance.NORMAL, createdAt = Calendar.getInstance().apply {
            set(2024, Calendar.APRIL, 10)
        }.time, modifiedAt = Calendar.getInstance().apply {
            set(2024, Calendar.APRIL, 11)
        }.time))
    }

    fun getTodoItems(): List<TodoItem> {
        return viewModel.items;
    }

    fun saveItem(item: TodoItem) {
        viewModel.saveItem(item)
    }

    fun deleteItem(itemId: String) {
        viewModel.deleteItem(itemId)
    }

    fun completeItem(itemId: String) {
        viewModel.completeItem(itemId)
    }

    fun setVisibilityOfCompletedItems(isVisible: Boolean) {
        viewModel.setVisibilityOfCompletedItems(isVisible)
    }
}