package com.androidfactory.fakestore.home.list

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.extensions.toDp
import com.androidfactory.fakestore.extensions.toPx
import com.androidfactory.fakestore.model.domain.Filter
import kotlinx.coroutines.launch
import java.util.*

class UiProductEpoxyController(
    private val viewModel: ProductsListViewModel
): TypedEpoxyController<ProductsListFragmentUiState>() {

    override fun buildModels(data: ProductsListFragmentUiState?) {
        when (data) {
            is ProductsListFragmentUiState.Success -> {
                val uiFilterModels = data.filters.map { uiFilter ->
                    UiProductFilterEpoxyModel(
                        uiFilter = uiFilter,
                        onFilterSelected = ::onFilterSelected
                    ).id(uiFilter.filter.value)
                }
                CarouselModel_()
                    .models(uiFilterModels)
                    .padding(Carousel.Padding(16.toPx(), 8.toPx()))
                    .id("filters")
                    .addTo(this)

                data.products.forEach { uiProduct ->
                    UiProductEpoxyModel(
                        uiProduct = uiProduct,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
            is ProductsListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    UiProductEpoxyModel(
                        uiProduct = null,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked
                    ).id(epoxyId).addTo(this)
                }
            }
            else -> {
                throw RuntimeException("Unhandled branch! $data")
            }
        }
    }

    private fun onFavoriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentFavoriteIds = currentState.favoriteProductIds
                val newFavoriteIds = if (currentFavoriteIds.contains(selectedProductId)) {
                    currentFavoriteIds.filter { it != selectedProductId }.toSet()
                } else {
                    currentFavoriteIds + setOf(selectedProductId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }

    private fun onUiProductClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentExpandedIds = currentState.expandedProductIds
                val newExpandedIds = if (currentExpandedIds.contains(productId)) {
                    currentExpandedIds.filter { it != productId }.toSet()
                } else {
                    currentExpandedIds + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpandedIds)
            }
        }
    }

    private fun onFilterSelected(filter: Filter) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentlySelectedFilter = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentlySelectedFilter != filter) {
                            filter
                        } else {
                            null
                        }
                    )
                )
            }
        }
    }
}