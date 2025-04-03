package com.example.proyecto_listado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_listado.ui.PlayerScreen
import com.example.proyecto_listado.ui.theme.Proyecto_ListadoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_ListadoTheme {
                PlayerScreen()
            }
        }
    }
}