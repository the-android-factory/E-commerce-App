package com.androidfactory.fakestore

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.model.domain.Product

class ProductEpoxyController: TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(product = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}