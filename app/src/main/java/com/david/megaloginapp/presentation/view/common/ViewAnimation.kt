package com.david.megaloginapp.presentation.view.common

import androidx.annotation.DimenRes
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ViewAnimation(
    @RawRes animationFile: Int,
    @DimenRes width: Int,
    @DimenRes height: Int,
) {
    val animationSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationFile))
    val progress by animateLottieCompositionAsState(
        composition = animationSpec,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        modifier = Modifier
            .width(dimensionResource(id = width))
            .height(dimensionResource(id = height)),
        composition = animationSpec,
        progress = progress,
        contentScale = ContentScale.Crop,
    )
}
