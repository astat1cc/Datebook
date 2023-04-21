package com.github.astat1cc.datebook.datelist.presentation

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.core.ui.colors.greenLight
import com.github.astat1cc.datebook.datelist.presentation.views.HourIntervalView
import org.koin.androidx.compose.getViewModel

@Composable
fun DateListScreen(
    navController: NavHostController,
    viewModel: DateListViewModel = getViewModel()
) {

    val dateMapState = viewModel.dateMap.collectAsState()

    val dateMap = dateMapState.value
    val hourList = viewModel.hourList

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AndroidView(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    factory = {
                        CalendarView(it)
                    },
                    update = { calendarView ->
                        calendarView.setOnDateChangeListener { _, year, month, day ->
                            viewModel.dateChanged(year, month, day)
                        }
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
            when (dateMap) {
                is UiState.Loading -> {}
                is UiState.Fail -> {}
                is UiState.Success -> {
                    items(hourList) { hour ->
                        HourIntervalView(hour = hour, dateList = dateMap.data[hour] ?: emptyList())
                    }
                }
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    viewModel.createDate()
                }
                .background(greenLight)
                .padding(16.dp),
            painter = painterResource(id = com.github.astat1cc.datebook.R.drawable.ic_add),
            contentDescription = null,
            tint = greenDark
        )
    }
}