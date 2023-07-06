package com.david.megaloginapp.presentation.view.common

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
