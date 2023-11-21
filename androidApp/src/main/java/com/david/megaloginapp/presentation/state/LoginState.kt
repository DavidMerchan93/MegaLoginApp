package com.david.megaloginapp.presentation.state

import androidx.annotation.StringRes
import com.david.megaloginapp.R
import com.david.megaloginapp.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val userLoggedSuccess: User? = null,
    val errorEmptyPassword: LoginErrors? = null,
    val errorInvalidEmail: LoginErrors? = null,
    val errorUserNotFound: LoginErrors? = null,

) {
    enum class LoginErrors(@StringRes val message: Int) {
        EMPTY_PASSWORD(R.string.login_error_invalid_password),
        EMAIL_FORMAT(R.string.login_error_invalid_email),
        USER_NOT_FOUND(R.string.login_error_message_user_not_found),
    }
}
