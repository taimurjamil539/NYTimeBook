package com.example.nytimesbooksapp.data.common.mapper

import com.example.nytimesbooksapp.data.local.Bookentity
import com.example.nytimesbooksapp.data.model.Book
import com.example.nytimesbooksapp.domain.model.Bookmodel

fun Book.toDomain(): Bookmodel{
    return Bookmodel(
        title = title,
        author = author,
        description = description,
        bookimage = book_image,
        publisher = publisher,
        rank = rank,
        createdDate = created_date,
        buylink = buy_links.firstOrNull()?.url?:""

    )
}
fun Book.toEntity(): Bookentity {
    return Bookentity(
        title = title,
        auther = author,
        image = book_image,
        description = description,
        publisher = publisher,
        rank = rank,
        createDate = created_date,
        buylink = buy_links.firstOrNull()?.url?:""


    )
}

fun Bookentity.toDomain(): Bookmodel{

    return Bookmodel(
        title = title,
        author = auther,
        description = description,
        bookimage = image,
        publisher = publisher,
        rank = rank,
        createdDate = createDate,
        buylink = buylink


        )
}