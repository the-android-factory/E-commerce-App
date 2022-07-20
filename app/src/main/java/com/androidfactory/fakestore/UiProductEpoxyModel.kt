package com.androidfactory.fakestore

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.androidfactory.fakestore.databinding.EpoxyModelProductItemBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.ui.UiProduct
import java.text.NumberFormat

data class UiProductEpoxyModel(
    val uiProduct: UiProduct?
) : ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelProductItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let { uiProduct ->
            shimmerLayout.stopShimmer()

            // Setup our text
            productTitleTextView.text = uiProduct.product.title
            productDescriptionTextView.text = uiProduct.product.description
            productCategoryTextView.text = uiProduct.product.category
            productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)

            // Favorite icon
            val imageRes = if (uiProduct.isFavorite) {
                R.drawable.ic_round_favorite_24
            } else {
                R.drawable.ic_round_favorite_border_24
            }
            favoriteImageView.setIconResource(imageRes)

            // Load our image
            productImageViewLoadingProgressBar.isVisible = true
            productImageView.load(data = uiProduct.product.image) {
                listener { request, result ->
                    productImageViewLoadingProgressBar.isGone = true
                }
            }
        } ?: shimmerLayout.startShimmer()
    }
}
