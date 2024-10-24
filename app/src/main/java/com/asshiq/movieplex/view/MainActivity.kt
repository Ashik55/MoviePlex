package com.asshiq.movieplex.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asshiq.movieplex.api.ApiState
import com.asshiq.movieplex.data.Post
import com.asshiq.movieplex.ui.theme.MoviePlexTheme
import dagger.hilt.android.AndroidEntryPoint


object Routes {
    const val Home = "Home"
    const val DETAILS_SCREEN = "DETAILS_SCREEN"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviePlexTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable(Routes.Home) { HomeScreen(navController) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    // Observe the states from the ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val postList by viewModel.postList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top news") },
            )
        }
    ) { padding ->

        // Access the current context to show a toast
        val context = LocalContext.current

        // If there's an error, display an error message
        if (errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        } else if (isLoading) {
            // Show loading indicator if loading
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display the post list once data is loaded
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(postList ?: emptyList()) { post ->
                    EachRow(post = post) {
                        //clicked
                        Toast.makeText(context, "This is a Toast!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Composable
fun EachRow(post: Post, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                onClick?.invoke()
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(text = post.body!!, modifier = Modifier.padding(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviePlexTheme {
//        Greeting("Android")
    }
}