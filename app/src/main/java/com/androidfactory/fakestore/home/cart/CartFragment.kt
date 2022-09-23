package com.androidfactory.fakestore.home.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.androidfactory.fakestore.MainActivity
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.FragmentCartBinding
import com.androidfactory.fakestore.home.cart.epoxy.CartFragmentEpoxyController
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.ui.UiProduct
import com.androidfactory.fakestore.model.ui.UiProductInCart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel by viewModels<CartFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CartFragmentEpoxyController(viewModel, onEmptyStateClicked = {
            (activity as? MainActivity)?.navigateToTab(R.id.productsListFragment)
        })
        binding.epoxyRecyclerView.setController(epoxyController)

        val uiProductsInCartFlow = viewModel.uiProductListReducer.reduce(store = viewModel.store).map { uiProducts ->
            uiProducts.filter { it.isInCart }
        }

        combine(
            uiProductsInCartFlow,
            viewModel.store.stateFlow.map { it.cartQuantitiesMap }
        ) { uiProducts, quantityMap ->
            uiProducts.map {
                UiProductInCart(uiProduct = it, quantity = quantityMap[it.product.id] ?: 1)
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiProducts ->
            val viewState = if (uiProducts.isEmpty()) {
                UiState.Empty
            } else {
                UiState.NonEmpty(uiProducts)
            }
            epoxyController.setData(viewState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    sealed interface UiState {
        object Empty: UiState
        data class NonEmpty(val products: List<UiProductInCart>): UiState
    }
}