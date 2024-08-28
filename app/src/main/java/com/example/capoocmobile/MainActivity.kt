package com.example.capoocmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.capoocmobile.view_models.MainViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.capoocmobile.views.DetailsView
import com.example.capoocmobile.views.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = remember { MainViewModel() }
            Box(modifier = Modifier.fillMaxSize()) {
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "chooseDevice") {
        composable("chooseDevice") {
            MainView(navController, viewModel)
        }
        composable("details") {
            DetailsView(navController, viewModel)
        }
    }
}