package com.david.megaloginapp.presentation.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.david.megaloginapp.R
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.ViewAnimation
import com.david.megaloginapp.presentation.viewModel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), onLogout: () -> Unit) {
    val userHomeState = homeViewModel.userHomeState
    val userLogoutState = homeViewModel.userLogoutState

    LaunchedEffect(userLogoutState) {
        if (userLogoutState) {
            onLogout()
        }
    }

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
        if (userHomeState.userData != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.home_title_welcome,
                    userHomeState.userData.name,
                ),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.home_message_loading),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        }
        SimpleButton(
            label = stringResource(id = R.string.home_button_logout),
            isLoading = userHomeState.isLoading,
        ) {
            homeViewModel.logout()
        }
    }
}
