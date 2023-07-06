package com.david.megaloginapp.presentation.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.david.megaloginapp.R
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.ViewAnimation

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.dimen_40dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(id = R.dimen.dimen_40dp),
            alignment = Alignment.CenterVertically,
        ),
    ) {
        ViewAnimation(
            animationFile = R.raw.astronaut_in_space,
            width = R.dimen.dimen_150dp,
            height = R.dimen.dimen_150dp,
        )
        Text(text = stringResource(id = R.string.home_title_welcome), fontSize = 20.sp)
        SimpleButton(label = stringResource(id = R.string.home_button_logout)) {
        }
    }
}
