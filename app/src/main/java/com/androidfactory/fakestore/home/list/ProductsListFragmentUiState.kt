package com.androidfactory.fakestore.home.list

import com.androidfactory.fakestore.model.ui.UiFilter
import com.androidfactory.fakestore.model.ui.UiProduct

sealed interface ProductsListFragmentUiState {

    data class Success(
        val filters: Set<UiFilter>,
        val products: List<UiProduct>
    ): ProductsListFragmentUiState

    object Loading: ProductsListFragmentUiState
}