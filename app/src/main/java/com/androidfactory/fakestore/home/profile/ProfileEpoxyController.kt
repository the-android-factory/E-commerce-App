package com.androidfactory.fakestore.home.profile

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelProfileSignedInItemBinding
import com.androidfactory.fakestore.databinding.EpoxyModelProfileSignedOutBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel
import com.androidfactory.fakestore.extensions.toPx
import com.androidfactory.fakestore.home.cart.epoxy.DividerEpoxyModel
import com.androidfactory.fakestore.redux.ApplicationState

class ProfileEpoxyController(
    private val userProfileItemGenerator: UserProfileItemGenerator,
    private val profileUiActions: ProfileUiActions
) : TypedEpoxyController<ApplicationState.AuthState>() {

    override fun buildModels(data: ApplicationState.AuthState) {
        if (data is ApplicationState.AuthState.Unauthenticated) {
            SignedOutEpoxyModel(
                onSignIn = { username, password ->
                    profileUiActions.onSignIn(username, password)
                },
                errorMessage = data.errorString
            ).id("signed_out_state").addTo(this)
        }

        if (data is ApplicationState.AuthState.Authenticated) {
            userProfileItemGenerator.buildItems(user = data.user).forEach { profileItem ->
                SignedInItemEpoxyModel(
                    iconRes = profileItem.iconRes,
                    headerText = profileItem.headerText,
                    infoText = profileItem.infoText,
                    onClick = { profileUiActions.onProfileItemSelected(profileItem.iconRes) }
                ).id(profileItem.iconRes).addTo(this)

                DividerEpoxyModel(
                    horizontalMargin = 20.toPx()
                ).id("divider_${profileItem.iconRes}").addTo(this)
            }

            SignedInItemEpoxyModel(
                iconRes = R.drawable.ic_round_logout_24,
                headerText = "Logout",
                infoText = "Sign out of your account",
                onClick = { profileUiActions.onProfileItemSelected(R.drawable.ic_round_logout_24) }
            ).id(R.drawable.ic_round_logout_24).addTo(this)
        }
    }

    data class SignedOutEpoxyModel(
        val onSignIn: (String, String) -> Unit,
        val errorMessage: String?
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedOutBinding>(R.layout.epoxy_model_profile_signed_out) {

        override fun EpoxyModelProfileSignedOutBinding.bind() {
            passwordLayout.error = errorMessage
            signInButton.setOnClickListener {
                val username = usernameEditText.text?.toString()
                val password = passwordEditText.text?.toString()

                if (username.isNullOrBlank() || password.isNullOrBlank()) {
                    passwordLayout.error = "Both fields required"
                    return@setOnClickListener
                }

                passwordLayout.error = null
                onSignIn(username, password)
            }
        }

        override fun EpoxyModelProfileSignedOutBinding.unbind() {
            usernameEditText.text = null
            usernameEditText.clearFocus()
            passwordEditText.text = null
            passwordEditText.clearFocus()
            passwordLayout.error = null
        }
    }

    data class SignedInItemEpoxyModel(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String,
        val onClick: () -> Unit
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedInItemBinding>(R.layout.epoxy_model_profile_signed_in_item) {

        override fun EpoxyModelProfileSignedInItemBinding.bind() {
            iconImageView.setImageResource(iconRes)
            headerTextView.text = headerText
            infoTextView.text = infoText
            root.setOnClickListener { onClick() }
        }
    }
}