package com.androidfactory.fakestore

import com.androidfactory.fakestore.model.ui.UiFilter
import com.androidfactory.fakestore.model.ui.UiProduct

data class ProductsListFragmentUiState(
    val filters: Set<UiFilter>,
    val products: List<UiProduct>
)
