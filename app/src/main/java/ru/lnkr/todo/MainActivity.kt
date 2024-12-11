package ru.lnkr.todo

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.lnkr.todo.navigation.MainNavGraph
import ru.lnkr.todo.repository.TodoViewModel
import ru.lnkr.todo.ui.theme.AppTheme

@SuppressLint("CompositionLocalNaming")
val VMCompositionLocal = staticCompositionLocalOf<TodoViewModel> {
    error("No TodoViewModel provided")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TodoViewModel = viewModel()
            CompositionLocalProvider(VMCompositionLocal provides viewModel) {
                MainActivityComponent()
            }
        }
    }
}

@Composable
fun MainActivityComponent() {
    AppTheme {
        val navController = rememberNavController()
        Box(
            modifier = Modifier
                .background(AppTheme.colors.backPrimary)
        ) {
            MainNavGraph(navController = navController)
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
    CompositionLocalProvider(VMCompositionLocal provides fakeViewModel) {
        MainActivityComponent()
    }
}