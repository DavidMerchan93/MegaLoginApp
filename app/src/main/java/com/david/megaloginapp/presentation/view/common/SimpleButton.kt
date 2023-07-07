package com.david.megaloginapp.presentation.view.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.david.megaloginapp.R

@Composable
fun SimpleButton(
    label: String,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dimen_40dp)),
        onClick = onClick,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_24dp)),
                color = Color.White,
            )
        } else {
            Text(
                text = label,
                fontSize = 18.sp,
            )
        }
    }
}
