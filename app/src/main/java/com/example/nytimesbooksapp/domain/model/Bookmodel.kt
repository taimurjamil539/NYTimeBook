package com.example.nytimesbooksapp.domain.model

data class Bookmodel(
    val title: String,
    val bookimage: String,
    val description: String,
    val rank: Int,
    val publisher: String,
    val author: String,
    val buylink: String,
    val createdDate: String
)
