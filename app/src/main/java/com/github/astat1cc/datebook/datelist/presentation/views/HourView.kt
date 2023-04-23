package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HourView(modifier: Modifier = Modifier, hour: String) {
    Text(
        modifier = modifier,
        text = "$hour:00",
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
}