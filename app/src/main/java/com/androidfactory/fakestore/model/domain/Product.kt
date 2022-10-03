package com.androidfactory.fakestore.model.domain

import java.math.BigDecimal

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: BigDecimal,
    val title: String,
    val rating: Rating
) {
    data class Rating(
        val value: Double,
        val numberOfRatings: Int
    )
}
