package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// hour is given in "01", "02", etc format
@Composable
fun HourIntervalView(hour: String, modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // hour
        HourView(hour = hour)
        // divider aligned with hour view
        Box(
            modifier = Modifier
                .padding(start = 24.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
//        Box(modifier = Modifier.padding(start = 24.dp), contentAlignment = Alignment.Center) {
//            Box(
//                modifier = Modifier
//                    .padding(start = 24.dp)
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(Color.LightGray)
//            )
////            Canvas(
////                Modifier
////                    .fillMaxWidth()
////                    .height(1.dp)
////            ) {
////                drawLine(
////                    color = Color.LightGray,
////                    start = Offset(0f, 0f),
////                    end = Offset(size.width, 0f),
////                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 25f), 0f)
////                )
////            }
////            Text(text = hour, modifier = Modifier.alpha(0f))
//        }

    }
}