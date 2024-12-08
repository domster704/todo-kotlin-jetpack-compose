package ru.lnkr.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.TodoEditScreen
import ru.lnkr.todo.ui.TodoListScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "todo_list"
    ) {
        composable("todo_list") {
            TodoListScreen(
                onAddClick = { navController.navigate("todo_edit") },
                onItemClick = { item ->
                    navController.navigate("todo_edit/${item.id}")
                },
            )
        }
        composable("todo_edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val item = TodoItemsRepository.getTodoItems().find { it.id == taskId }
            TodoEditScreen(
                item = item,
                onSave = { updatedItem ->
                    TodoItemsRepository.saveItem(updatedItem)
                },
                onDelete = { id ->
                    TodoItemsRepository.deleteItem(id.toString())
                },
                onClose = { navController.popBackStack() },
                onComplete = { id ->
                    TodoItemsRepository.completeItem(id.toString())
                }
            )
        }
        composable("todo_edit") {
            TodoEditScreen(
                item = null,
                onSave = { newItem ->
                    TodoItemsRepository.saveItem(newItem)
                },
                onClose = { navController.popBackStack() }
            )
        }
    }
}


