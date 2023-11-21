package com.david.megaloginapp.presentation.view.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.david.megaloginapp.R

@Composable
fun TextInput(
    value: String,
    inputType: InputType,
    @StringRes messageError: Int?,
    isEnabled: Boolean = false,
    onChangeValue: (value: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onChangeValue(it) },
            leadingIcon = {
                Icon(imageVector = inputType.icon, contentDescription = null)
            },
            label = {
                Text(text = stringResource(id = inputType.label))
            },
            singleLine = true,
            keyboardOptions = inputType.keyboardOptions,
            visualTransformation = inputType.visualTransformation,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            isError = messageError != null,
            enabled = isEnabled,
        )
        if (messageError != null) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.dimen_4dp)),
                text = stringResource(id = messageError),
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
