package com.androidfactory.fakestore.model.network

import com.androidfactory.fakestore.model.domain.user.Name

data class NetworkUser(
    val __v: Int,
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String
) {
    data class Address(
        val city: String,
        val geolocation: Geolocation,
        val number: Int,
        val street: String,
        val zipcode: String
    ) {
        data class Geolocation(
            val lat: String,
            val long: String
        )
    }
}