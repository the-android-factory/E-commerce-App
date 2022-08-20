package com.androidfactory.fakestore.hilt.repository

import com.androidfactory.fakestore.hilt.service.ProductsService
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
) {

    suspend fun fetchAllProducts(): List<Product> {
        // todo better error handling
        return productsService.getAllProducts().body()?.let { networkProducts ->
            networkProducts.map { productMapper.buildFrom(it) }
        } ?: emptyList()
    }
}