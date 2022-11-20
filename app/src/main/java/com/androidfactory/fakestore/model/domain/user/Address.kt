package com.androidfactory.fakestore.model.domain.user

data class Address(
    val city: String,
    val number: Int,
    val street: String,
    val zipcode: String,
    val lat: String,
    val long: String
)