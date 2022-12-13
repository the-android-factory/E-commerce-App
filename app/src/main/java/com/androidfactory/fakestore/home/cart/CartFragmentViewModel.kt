package com.androidfactory.fakestore.home.cart

import androidx.lifecycle.ViewModel
import com.androidfactory.fakestore.redux.ApplicationState
import com.androidfactory.fakestore.redux.Store
import com.androidfactory.fakestore.redux.reducer.UiProductListReducer
import com.androidfactory.fakestore.redux.updater.UiProductFavoriteUpdater
import com.androidfactory.fakestore.redux.updater.UiProductInCartUpdater
import com.androidfactory.fakestore.redux.updater.UiProductQuantityUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    val uiProductQuantityUpdater: UiProductQuantityUpdater
): ViewModel() {
}