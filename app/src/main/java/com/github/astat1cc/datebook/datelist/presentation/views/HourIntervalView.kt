package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.datelist.presentation.models.DateListItemUi

// hour is given in "01", "02", etc format
@Composable
fun HourIntervalView(hour: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // hour
        Text(
            text = "$hour:00",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
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