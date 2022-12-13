package com.androidfactory.fakestore.home.explore

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExploreFragmentActions(private val viewModel: ExploreViewModel) {

    fun onProductSelected(productId: Int) {
        viewModel.onProductSelected(productId)
    }

    fun onQuantityChanged(productId: Int, quantity: Int) = viewModel.viewModelScope.launch {
        viewModel.store.update { currentApplicationState ->
            return@update viewModel.uiProductQuantityUpdater.update(
                productId = productId,
                newQuantity = quantity,
                currentState = currentApplicationState
            )
        }
    }

    fun onAddToCart(productId: Int) = viewModel.viewModelScope.launch {
        viewModel.store.update { currentApplicationState ->
            return@update viewModel.uiProductInCartUpdater.update(
                productId = productId,
                currentState = currentApplicationState
            )
        }
    }
}