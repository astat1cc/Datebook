package com.github.astat1cc.datebook.datelist.presentation

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.astat1cc.datebook.datelist.presentation.views.HourIntervalView
import org.koin.androidx.compose.getViewModel

@Composable
fun DateListScreen(
    navController: NavHostController,
    viewModel: DateListViewModel = getViewModel()
) {

    val dateList = viewModel.dateList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                factory = { CalendarView(it) },
                update = { calendarView ->
                    calendarView.setOnDateChangeListener { _, year, month, day ->
                        viewModel.dateChanged(year, month, day)
                    }
                }
            )
        }
        item { HourIntervalView(hour = "15:00", emptyList()) }
    }
}