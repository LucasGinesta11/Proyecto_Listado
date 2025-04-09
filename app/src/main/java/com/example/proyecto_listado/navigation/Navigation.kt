package com.example.proyecto_listado.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_listado.model.TransitionType
import com.example.proyecto_listado.model.TransitionType.FADE
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
        composable("PlayerScreen/{option}") { backStackEntry ->
            val optionString = backStackEntry.arguments?.getString("option") ?: ""
            val option = TransitionType.entries.find { it.name == optionString } ?: FADE
            PlayerScreen(option, navController)
        }
    }
}