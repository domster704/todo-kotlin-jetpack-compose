package ru.lnkr.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ru.lnkr.todo.navigation.MainNavGraph
import ru.lnkr.todo.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                MainNavGraph(navController = navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    AppTheme {
        val navController = rememberNavController()
        MainNavGraph(navController = navController)
    }
}