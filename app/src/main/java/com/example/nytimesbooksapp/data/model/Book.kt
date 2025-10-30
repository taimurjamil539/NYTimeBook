package com.example.nytimesbooksapp.data.model

data class Book(

    val author: String,
    val book_image: String,
    val buy_links: List<BuyLink>,
    val created_date: String,
    val description: String,
    val publisher: String,
    val rank: Int,
    val title: String,

)