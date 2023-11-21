package com.david.megaloginapp.presentation.view.common

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.david.megaloginapp.R

sealed class InputType(
    @StringRes val label: Int,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
) {

    data class Email(val isRegister: Boolean = false) : InputType(
        label = if (isRegister) R.string.register_hint_email else R.string.login_hint_email,
        icon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        visualTransformation = VisualTransformation.None,
    )

    data class Password(val isRegister: Boolean = false) : InputType(
        label = if (isRegister) R.string.register_hint_password else R.string.login_hint_password,
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
    )

    object FullName : InputType(
        label = R.string.register_hint_full_name,
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
        ),
        visualTransformation = VisualTransformation.None,
    )

    object ConfirmPassword : InputType(
        label = R.string.register_hint_repeat_password,
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
    )
}
