package com.androidfactory.fakestore

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.model.ui.UiProduct

class UiProductEpoxyController: TypedEpoxyController<List<UiProduct>>() {

    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                UiProductEpoxyModel(uiProduct = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { uiProduct ->
            UiProductEpoxyModel(uiProduct).id(uiProduct.product.id).addTo(this)
        }
    }
}