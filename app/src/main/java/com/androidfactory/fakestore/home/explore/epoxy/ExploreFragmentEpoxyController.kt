package com.androidfactory.fakestore.home.explore.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.home.explore.ExploreFragmentActions
import com.androidfactory.fakestore.home.explore.ExploreFragmentViewState

class ExploreFragmentEpoxyController(
    private val actions: ExploreFragmentActions
) : TypedEpoxyController<ExploreFragmentViewState>() {

    override fun buildModels(data: ExploreFragmentViewState) = when (data) {
        ExploreFragmentViewState.Loading -> {
            // Implement loading state
        }
        is ExploreFragmentViewState.Error -> {
            // Implement your error here
        }
        is ExploreFragmentViewState.Success -> {
            HeaderImageEpoxyModel(data.selectedUiProduct.product.image)
                .id("header_image")
                .addTo(this)

            val circleImageEpoxyModels: List<EpoxyModel<*>> = data.allUiProducts.map { uiProduct ->
                CircleImageEpoxyModel(
                    imageUrl = uiProduct.product.image,
                    isSelected = uiProduct.product.id == data.selectedUiProduct.product.id,
                    onClick = { actions.onProductSelected(uiProduct.product.id) }
                ).id("image_${uiProduct.product.id}")
            }

            CarouselModel_().models(circleImageEpoxyModels).id("carousel").addTo(this)

            ProductHeaderDescriptionEpoxyModel(data.selectedUiProduct)
                .id(data.selectedUiProduct.product.id)
                .addTo(this)

            ProductFooterEpoxyModel(
                quantity = data.quantity,
                uiProduct = data.selectedUiProduct,
                addToCart = {
                    actions.onAddToCart(productId = data.selectedUiProduct.product.id)
                },
                onQuantityUpdate = { newQuantity ->
                    actions.onQuantityChanged(
                        productId = data.selectedUiProduct.product.id,
                        quantity = newQuantity
                    )
                }
            ).id("footer_${data.selectedUiProduct.product.id}").addTo(this)
        }
    }
}