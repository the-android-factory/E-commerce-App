package com.androidfactory.fakestore.home.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.hilt.repository.ProductsRepository
import com.androidfactory.fakestore.model.domain.Filter
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.redux.ApplicationState
import com.androidfactory.fakestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductsRepository,
    private val filterGenerator: FilterGenerator
) : ViewModel() {

    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        val filters: Set<Filter> = filterGenerator.generateFrom(products)
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filters,
                    selectedFilter = null
                )
            )
        }
    }
}