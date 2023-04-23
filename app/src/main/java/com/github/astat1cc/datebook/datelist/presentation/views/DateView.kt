package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.astat1cc.datebook.core.ui.colors.green
import com.github.astat1cc.datebook.core.ui.colors.greenDark

@Composable
fun DateView(modifier: Modifier = Modifier, name: String, time: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(green)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = greenDark
            )
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = com.github.astat1cc.datebook.R.drawable.ic_clock),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = time.substringAfterLast(" "), // takes only time
                    fontSize = 12.sp,
                    color = greenDark
                )
            }
        }
    }
}