package com.androidfactory.fakestore.home.cart.epoxy

import androidx.annotation.Dimension
import androidx.annotation.Dimension.PX
import androidx.core.view.updatePadding
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelDividerBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel

data class DividerEpoxyModel(
    @Dimension(unit = PX) private val horizontalPadding: Int = 0,
    @Dimension(unit = PX) private val verticalPadding: Int = 0
) : ViewBindingKotlinModel<EpoxyModelDividerBinding>(R.layout.epoxy_model_divider) {

    override fun EpoxyModelDividerBinding.bind() {
        root.updatePadding(
            left = horizontalPadding, right = horizontalPadding,
            top = verticalPadding, bottom = verticalPadding
        )
    }
}