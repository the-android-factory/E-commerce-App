package com.androidfactory.fakestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidfactory.fakestore.databinding.ActivityMainBinding
import com.androidfactory.fakestore.hilt.service.ProductsService
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.androidfactory.fakestore.model.network.NetworkProduct
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)
        controller.setData(emptyList())

        lifecycleScope.launchWhenStarted {
            val response: Response<List<NetworkProduct>> = productsService.getAllProducts()
            val domainProducts: List<Product> = response.body()?.map {
                productMapper.buildFrom(networkProduct = it)
            } ?: emptyList()
            controller.setData(domainProducts)

            if (domainProducts.isEmpty()) {
                Snackbar.make(binding.root, "Failed to fetch", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        /*binding.cardView.setOnClickListener {
            binding.productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        binding.addToCartButton.setOnClickListener {
            binding.inCartView.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite = false
        binding.favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_round_favorite_border_24
            } else {
                R.drawable.ic_round_favorite_24
            }
            binding.favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite
        }*/
    }
}