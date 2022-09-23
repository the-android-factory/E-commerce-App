package com.androidfactory.fakestore.home.cart.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelCartProductItemBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.model.ui.UiProductInCart

data class CartItemEpoxyModel(
    private val uiProductInCart: UiProductInCart,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteClicked: () -> Unit,
    private val onDeleteClicked: () -> Unit,
    private val onQuantityChanged: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item) {

    override fun EpoxyModelCartProductItemBinding.bind() {
        // Setup our text
        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        // Favorite icon
        val imageRes = if (uiProductInCart.uiProduct.isFavorite) {
            R.drawable.ic_round_favorite_24
        } else {
            R.drawable.ic_round_favorite_border_24
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener { onFavoriteClicked() }

        deleteIconImageView.setOnClickListener { onDeleteClicked() }

        // Load our image
        productImageView.load(data = uiProductInCart.uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

        quantityView.apply {
            quantityTextView.text = uiProductInCart.quantity.toString()
            minusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity - 1) }
            plusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity + 1) }
        }
    }
}