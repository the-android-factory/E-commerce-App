package com.androidfactory.fakestore.redux

import com.androidfactory.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet()
)
