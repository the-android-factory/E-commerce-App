package com.androidfactory.fakestore.hilt.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.model.network.LoginResponse
import com.androidfactory.fakestore.model.network.NetworkUser
import com.androidfactory.fakestore.redux.ApplicationState
import com.androidfactory.fakestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository
): ViewModel() {

    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update { it.copy(user = donUserResponse.body()) }

            if (donUserResponse.body() == null) {
                Log.e("LOGIN", response.errorBody()?.toString() ?: response.message())
            }
        } else {
            Log.e("LOGIN", response.errorBody()?.byteStream()?.bufferedReader()?.readLine() ?: "Invalid login")
        }
    }
}