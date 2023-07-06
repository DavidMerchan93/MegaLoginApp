package com.david.megaloginapp.presentation.state

import androidx.annotation.StringRes
import com.david.megaloginapp.R

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val isSuccessChange: Boolean = false,
    val errorEmail: Error? = null,
    val errorPassword: Error? = null,
    val errorConfirmPassword: Error? = null,
    val errorUser: Error? = null,
) {
    enum class Error(@StringRes val message: Int) {
        EMPTY_EMAIL(R.string.forgot_password_error_require_email),
        EMPTY_PASSWORD(R.string.forgot_password_error_require_password),
        CONFIRM_PASSWORD(R.string.forgot_password_error_password_not_match),
        USER_NOT_FOUND(R.string.forgot_password_error_change_password),
    }
}
