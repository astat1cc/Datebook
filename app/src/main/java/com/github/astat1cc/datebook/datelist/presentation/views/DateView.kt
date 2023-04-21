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
import com.github.astat1cc.datebook.core.ui.colors.greenDark

@Composable
fun DateView(modifier: Modifier = Modifier, name: String, time: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Box(
            Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(greenDark)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
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
                    text = time,
                    fontSize = 12.sp
                )
            }
        }
//        Column(
//            modifier = Modifier
//                .padding(start = 8.dp)
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(8.dp)
//        ) {
//            Text(
//                text = name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 14.sp
//            )
//            Row(
//                modifier = Modifier.padding(top = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    modifier = Modifier.size(12.dp),
//                    painter = painterResource(id = com.github.astat1cc.datebook.R.drawable.ic_clock),
//                    contentDescription = null
//                )
//                Text(
//                    modifier = Modifier.padding(start = 4.dp),
//                    text = time,
//                    fontSize = 12.sp
//                )
//            }
//        }
    }
}