package com.androidfactory.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val productsService = retrofit.create(ProductsService::class.java)

        lifecycleScope.launchWhenStarted {
            val response = productsService.getAllProducts()
            Log.i("DATA", response.body()!!.toString())
        }
    }

    interface ProductsService {
        @GET("products")
        suspend fun getAllProducts(): Response<List<Any>>
    }
}