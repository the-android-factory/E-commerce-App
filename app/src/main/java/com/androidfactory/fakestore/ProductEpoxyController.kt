package com.androidfactory.fakestore

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.model.domain.Product

class ProductEpoxyController: TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data == null || data.isEmpty()) {
            // todo loading state
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}