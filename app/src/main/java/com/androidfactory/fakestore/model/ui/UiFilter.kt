package com.androidfactory.fakestore.model.ui

import com.androidfactory.fakestore.model.domain.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
