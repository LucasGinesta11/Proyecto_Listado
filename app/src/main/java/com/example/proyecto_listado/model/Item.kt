package com.example.proyecto_listado.model

data class Item(val file: String, val type: MediaType, val duration: Int)

enum class MediaType {
    IMAGE,
    VIDEO,
    URL
}

object MediaData {
    val items = listOf(
        Item("imagen", type = MediaType.IMAGE, 4000),
        Item("imagen", type = MediaType.IMAGE, 4000),
        Item("video", type = MediaType.VIDEO, 5000),
        Item("https://orbys.eu/", type = MediaType.URL, 30000)
    )
}