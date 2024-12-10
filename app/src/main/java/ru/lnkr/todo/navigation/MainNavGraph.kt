package ru.lnkr.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.ui.TodoEditScreen
import ru.lnkr.todo.ui.TodoListScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    val vm = VMCompositionLocal.current

    NavHost(
        navController = navController,
        startDestination = "todo_list",
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
            val item = vm.items.find { it.id == taskId }
            TodoEditScreen(
                item = item,
                onSave = { updatedItem ->
                    vm.saveItem(updatedItem)
                },
                onDelete = { id ->
                    vm.deleteItem(id)
                },
                onClose = { navController.popBackStack() },
                onComplete = { id ->
                    vm.completeItem(id)
                }
            )
        }
        composable("todo_edit") {
            TodoEditScreen(
                item = null,
                onSave = { newItem ->
                    vm.saveItem(newItem)
                },
                onClose = { navController.popBackStack() }
            )
        }
    }
}


