package com.androidfactory.fakestore.model.domain.user

data class User(
    val id: Int,
    val name: Name,
    val email: String,
    val username: String,
    val phoneNumber: String,
    val address: Address
) {
    val greetingMessage: String = "Welcome back, ${name.firstname}"
}
