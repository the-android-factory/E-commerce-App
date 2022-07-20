package com.androidfactory.fakestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.redux.ApplicationState
import com.androidfactory.fakestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products
            )
        }

        delay(5000)
        store.update {
            return@update it.copy(favoriteProductIds = setOf(1, 2, 4))
        }
    }
}