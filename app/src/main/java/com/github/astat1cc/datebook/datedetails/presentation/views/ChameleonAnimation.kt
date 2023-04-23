package com.github.astat1cc.datebook.datedetails.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.github.astat1cc.datebook.R

@Composable
fun ChameleonAnimation() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chameleon_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .padding(top = 24.dp)
            .height(300.dp)
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxHeight(),
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Fit
        )
    }
}