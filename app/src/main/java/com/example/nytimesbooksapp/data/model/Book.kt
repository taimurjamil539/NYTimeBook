package com.example.nytimesbooksapp.data.model

data class Book(

    val author: String,
    val book_image: String,

    val buy_links: List<BuyLink>,
//    val contributor: String,
//    val contributor_note: String,
    val created_date: String,
//    val dagger: Int,
    val description: String,
//    val first_chapter_link: String,
//    val isbns: List<Isbn>,
//    val price: String,
//    val primary_isbn10: String,
//    val primary_isbn13: String,
    val publisher: String,
    val rank: Int,
//    val rank_last_week: Int,
//    val sunday_review_link: String,
    val title: String,
//    val updated_date: String,
//    val weeks_on_list: Int
)