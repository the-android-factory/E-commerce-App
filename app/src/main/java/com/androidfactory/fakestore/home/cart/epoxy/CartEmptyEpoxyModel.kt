package com.androidfactory.fakestore.home.cart.epoxy

import android.view.View
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelCartEmptyBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}
