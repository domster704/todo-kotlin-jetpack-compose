package ru.lnkr.todo.navigation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.repository.TodoViewModel
import ru.lnkr.todo.ui.TodoEditScreen
import ru.lnkr.todo.ui.TodoListScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    val vm = VMCompositionLocal.current
    val items = vm.items.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "todo_list",
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }
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
            val item = items.value.find { it.id == taskId }
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


@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MainActivityPreview() {
    val fakeViewModel = viewModel<TodoViewModel>()
    val navController = rememberNavController()
    CompositionLocalProvider(VMCompositionLocal provides fakeViewModel) {
        MainNavGraph(navController)
    }
}