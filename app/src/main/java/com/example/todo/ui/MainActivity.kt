package com.example.todo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.model.TodoData
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.viewmodel.TodoUIState
import com.example.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TodoApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(todoViewModel: TodoViewModel = viewModel()) {
    TodoScreen(uiState = todoViewModel.todoUIState)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Todos") }
            )
        },
        content = { innerPadding ->
            TodoScreen(uiState = todoViewModel.todoUIState, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun TodoScreen(uiState: TodoUIState, modifier: Modifier = Modifier) {
    when (uiState) {
        is TodoUIState.Loading -> LoadingScreen(modifier)
        is TodoUIState.Success -> TodoList(uiState.todos, modifier)
        is TodoUIState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun TodoList(todos: List<TodoData>, modifier: Modifier = Modifier) {
    LazyColumn (
        modifier = modifier
    ) {
        items(todos) { todo->
            Text(
                text = todo.title,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(text = "Error while reading data from the API", modifier = modifier)
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "Loading...", modifier = modifier)
}