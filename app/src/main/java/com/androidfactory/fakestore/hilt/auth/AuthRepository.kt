package com.androidfactory.fakestore.hilt.auth

import com.androidfactory.fakestore.model.domain.user.User
import com.androidfactory.fakestore.model.mapper.UserMapper
import com.androidfactory.fakestore.model.network.LoginResponse
import com.androidfactory.fakestore.model.network.NetworkUser
import com.androidfactory.fakestore.model.network.post.LoginPostBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authService: AuthService) {

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(LoginPostBody(username, password))
    }

    suspend fun fetchDon(): Response<NetworkUser> {
        return authService.fetchUser(userId = 4)
    }
}