package com.androidfactory.fakestore.home.list

import androidx.core.content.ContextCompat
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelProductFilterBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.model.domain.Filter
import com.androidfactory.fakestore.model.ui.UiFilter

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelected: (Filter) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {

    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        filterNameTextView.text = uiFilter.filter.displayText

        val cardBackgroundColorResId = if (uiFilter.isSelected) {
            R.color.purple_500
        } else {
            R.color.purple_200
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context, cardBackgroundColorResId))
    }
}
