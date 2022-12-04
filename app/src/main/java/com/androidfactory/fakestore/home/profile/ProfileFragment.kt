package com.androidfactory.fakestore.home.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.FragmentProfileBinding
import com.androidfactory.fakestore.hilt.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
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
            it.authState
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { authState ->
            epoxyController.setData(authState)
            binding.headerTextView.text = authState.getGreetingMessage()
            binding.infoTextView.text = authState.getEmail()
        }

        authViewModel.intentFlow.filterNotNull().asLiveData().observe(viewLifecycleOwner) { intent ->
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}