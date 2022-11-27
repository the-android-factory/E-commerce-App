package com.androidfactory.fakestore.home.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelProfileSignedInItemBinding
import com.androidfactory.fakestore.databinding.EpoxyModelProfileSignedOutBinding
import com.androidfactory.fakestore.databinding.FragmentProfileBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.extensions.toPx
import com.androidfactory.fakestore.hilt.auth.AuthViewModel
import com.androidfactory.fakestore.home.cart.epoxy.DividerEpoxyModel
import com.androidfactory.fakestore.model.domain.user.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    @Inject
    lateinit var profileItemGenerator: UserProfileItemGenerator

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val uiActions = ProfileUiActions(authViewModel)
        val epoxyController = ProfileEpoxyController(profileItemGenerator, uiActions)
        binding.epoxyRecyclerView.setController(epoxyController)

        authViewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { user ->
            epoxyController.setData(user)
            binding.headerTextView.text = user?.greetingMessage ?: "Sign in"
            binding.infoTextView.text = user?.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}