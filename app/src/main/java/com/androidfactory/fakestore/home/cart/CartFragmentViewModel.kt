package com.androidfactory.fakestore.home.cart

import androidx.lifecycle.ViewModel
import com.androidfactory.fakestore.redux.ApplicationState
import com.androidfactory.fakestore.redux.Store
import com.androidfactory.fakestore.redux.reducer.UiProductListReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer
): ViewModel() {
}