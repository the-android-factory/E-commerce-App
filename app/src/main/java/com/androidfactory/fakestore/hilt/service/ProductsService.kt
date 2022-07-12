package com.androidfactory.fakestore.hilt.service

import com.androidfactory.fakestore.model.network.NetworkProduct
import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}