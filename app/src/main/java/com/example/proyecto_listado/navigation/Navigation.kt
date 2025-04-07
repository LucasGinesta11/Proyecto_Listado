package com.example.proyecto_listado.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_listado.ui.HomeScreen
import com.example.proyecto_listado.ui.PlayerScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("PlayerScreen/{option}") {backStackEntry ->
            val option = backStackEntry.arguments?.getString("option") ?: ""
            PlayerScreen(option, navController)
        }
    }
}