package com.androidfactory.fakestore.home.profile

import androidx.annotation.DrawableRes
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.model.domain.user.User
import javax.inject.Inject

class UserProfileItemGenerator @Inject constructor() {

    data class UserProfileUiItem(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String
    )

    fun buildItems(user: User): List<UserProfileUiItem> {
        return buildList {
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_person_24,
                    headerText = "Username",
                    infoText = user.username
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_phone_24,
                    headerText = "Phone number",
                    infoText = user.phoneNumber
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_location_24,
                    headerText = "Location",
                    infoText = "${user.address.street}, ${user.address.city}, ${user.address.zipcode}"
                )
            )
        }
    }
}