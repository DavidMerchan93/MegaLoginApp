package com.david.megaloginapp.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.david.megaloginapp.R
import com.david.megaloginapp.presentation.viewModel.SplashScreenViewModel

@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel(),
    navigateToHome: (userId: Int) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val splashScreenState = splashScreenViewModel.splashScreenState

    LaunchedEffect(splashScreenState.isLoading) {
        when {
            splashScreenState.isLoading -> {
                splashScreenViewModel.checkIfUser()
            }

            (splashScreenState.isUserLogged && splashScreenState.user != null) -> {
                navigateToHome(splashScreenState.user.id)
            }

            (splashScreenState.isUserLogged.not()) -> {
                navigateToLogin()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.play_store_512),
            contentDescription = null,
            modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_150dp)),
        )
    }
}
