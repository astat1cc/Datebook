package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.astat1cc.datebook.datelist.presentation.models.DateListItemUi

@Composable
fun HourIntervalView(hour: String, dateList: List<DateListItemUi>) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = hour)
            Box(modifier = Modifier
                .padding(start = 16.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
            )
        }
    }
}