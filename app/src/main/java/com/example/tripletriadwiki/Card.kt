package com.example.tripletriadwiki

data class Card(
    var name: String = "",
    var photo: Int = 0,
    var id: Int = 0,
    var type: String = "",
    var level: Int = 0,
    var element: String = "",
    var attributes: Map<String, String> = mapOf()
)