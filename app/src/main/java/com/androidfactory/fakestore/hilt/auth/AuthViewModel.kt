package com.androidfactory.fakestore.hilt.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.model.mapper.UserMapper
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
    private val authRepository: AuthRepository,
    private val userMapper: UserMapper
): ViewModel() {

    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update { applicationState ->
                applicationState.copy(
                    user = donUserResponse.body()?.let { userMapper.buildFrom(it) }
                )
            }

            if (donUserResponse.body() == null) {
                Log.e("LOGIN", response.errorBody()?.toString() ?: response.message())
            }
        } else {
            Log.e("LOGIN", response.errorBody()?.byteStream()?.bufferedReader()?.readLine() ?: "Invalid login")
        }
    }

    fun logout() = viewModelScope.launch {
        store.update { applicationState -> applicationState.copy(user = null) }
        // traditionally make a call the the BE
    }
}