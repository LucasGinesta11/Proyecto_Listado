package com.example.proyecto_listado.viewModel

import androidx.lifecycle.ViewModel
import com.example.proyecto_listado.model.MediaData

class ViewModel: ViewModel() {

    val mediaItems = MediaData.items

}