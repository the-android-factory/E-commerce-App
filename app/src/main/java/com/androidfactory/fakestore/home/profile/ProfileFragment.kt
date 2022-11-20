package com.androidfactory.fakestore.home.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.FragmentProfileBinding
import com.androidfactory.fakestore.hilt.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.button.setOnClickListener {
            authViewModel.login(username = "donero", password = "ewedon")
        }

        authViewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { user ->
            Log.i("USER", user.toString())

            binding.label.text = user?.greetingMessage ?: "Sign in"
            binding.button.isEnabled = user == null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}