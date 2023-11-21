package com.david.megaloginapp.presentation.state

import androidx.annotation.StringRes
import com.david.megaloginapp.R
import com.david.megaloginapp.domain.model.User

data class RegisterState(
    val isLoading: Boolean = false,
    val successRegister: User? = null,
    val errorFullName: Errors? = null,
    val errorEmail: Errors? = null,
    val errorPassword: Errors? = null,
    val errorRepeatPassword: Errors? = null,
    val errorUser: Errors? = null,
) {
    enum class Errors(@StringRes val message: Int) {
        FULL_NAME(R.string.register_error_require_name),
        EMAIL(R.string.register_error_require_email),
        PASSWORD(R.string.register_error_require_password),
        REPEAT_PASSWORD(R.string.register_error_password_not_match),
        USER(R.string.register_error_register_user),
    }
}
