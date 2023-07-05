package com.david.megaloginapp.presentation.view.common

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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

    object Email : InputType(
        label = R.string.login_hint_email,
        icon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        visualTransformation = VisualTransformation.None,
    )

    object Password : InputType(
        label = R.string.login_hint_password,
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
    )
}
