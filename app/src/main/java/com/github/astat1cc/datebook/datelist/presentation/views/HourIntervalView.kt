package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.github.astat1cc.datebook.datelist.presentation.models.DateListItemUi

@Composable
fun HourIntervalView(hour: String, dateList: List<DateListItemUi>) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // hour
        Text(text = hour, color = Color.White)
        Column(modifier = Modifier.padding(start = 24.dp)) {
            // divider aligned with hour view
            Box(contentAlignment = Alignment.Center) {
//                Box(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .fillMaxWidth()
//                        .background(Color.LightGray)
//                )
                Canvas(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 25f), 0f)
                    )
                }
                Text(text = hour, modifier = Modifier.alpha(0f))
            }

            dateList.forEach { date ->
                // date
                DateView(
                    modifier = Modifier.padding(top = 8.dp),
                    name = date.name,
                    time = date.dateStart
                )
            }
        }
    }
}