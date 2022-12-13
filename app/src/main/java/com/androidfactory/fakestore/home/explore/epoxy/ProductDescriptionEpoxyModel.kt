package com.androidfactory.fakestore.home.explore.epoxy

import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelExploreProductHeaderBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.model.ui.UiProduct
import java.text.NumberFormat

data class ProductHeaderDescriptionEpoxyModel(
    val uiProduct: UiProduct
): ViewBindingKotlinModel<EpoxyModelExploreProductHeaderBinding>(
    R.layout.epoxy_model_explore_product_header
) {
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelExploreProductHeaderBinding.bind() {
        productTitleTextView.text = uiProduct.product.title
        productDescriptionTextView.text = uiProduct.product.description
        productCategoryTextView.text = uiProduct.product.category
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)
    }
}