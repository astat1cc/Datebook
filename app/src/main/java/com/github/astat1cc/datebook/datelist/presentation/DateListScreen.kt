package com.github.astat1cc.datebook.datelist.presentation

import android.app.TimePickerDialog
import android.widget.CalendarView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.core.ui.colors.green
import com.github.astat1cc.datebook.datelist.presentation.views.DateCreatingSheet
import com.github.astat1cc.datebook.datelist.presentation.views.DateView
import com.github.astat1cc.datebook.datelist.presentation.views.HourIntervalView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun DateListScreen(
    navController: NavHostController,
    viewModel: DateListViewModel = getViewModel()
) {

    val dateMapState = viewModel.dateMap.collectAsState()
    val currentlyChosenDateState = viewModel.currentlyChosenDate.collectAsState()
    val currentlyChosenDateInMillisState = viewModel.currentlyChosenDateInMillis.collectAsState()
    val newDateTitleState = viewModel.newDateTitle.collectAsState()
    val newDateDescriptionState = viewModel.newDateDescription.collectAsState()
    val newDatePickedTime = viewModel.newDatePickedTime.collectAsState()
    val showNewDateTimePicker = viewModel.showNewDateTimePicker.collectAsState()

    val dateMap = dateMapState.value
    val hourList = DateListViewModel.hourList

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minutes ->
            viewModel.pickTime(hour, minutes)
        },
        0,
        0,
        true
    )


//    LaunchedEffect(key1 = showDateCreatingSheet.value) {
//        if (showDateCreatingSheet.value) {
//            launch {
//                modalSheetState.show()
//            }
//        }
//    }

    if (showNewDateTimePicker.value) {
        timePickerDialog.show()
        viewModel.timePickerIsShown()
    }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            DateCreatingSheet(
                dateTitle = newDateTitleState.value,
                dateDescription = newDateDescriptionState.value,
                pickedTime = newDatePickedTime.value,
                currentlyChosenDate = currentlyChosenDateState.value,
                onTitleTextFieldValueChanged = { newTitle ->
                    viewModel.onTitleChanged(newTitle)
                },
                onDescriptionTextFieldValueChanged = { newDescription ->
                    viewModel.onDescriptionChanged(newDescription)
                },
                doneButtonClickListener = {
                    coroutineScope.launch {
                        modalSheetState.hide()
                        viewModel.createDate()
                    }
                },
                onPickTimeClicked = {
                    viewModel.onPickTimeClicked()
                }
            )
        }
    ) {
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AndroidView(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        factory = {
                            CalendarView(it)
                        },
                        update = { calendarView ->
                            calendarView.date = currentlyChosenDateInMillisState.value
                            calendarView.setOnDateChangeListener { _, year, month, day ->
                                viewModel.dateChanged(year, month, day)
                            }
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
                when (dateMap) {
                    is UiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        }
                    }
                    is UiState.Fail -> {}
                    is UiState.Success -> {
                        hourList.forEach { hour ->
                            item(
                                key = hour
                            ) { HourIntervalView(hour = hour) }
                            items(
                                items = dateMap.data[hour] ?: emptyList(),
                                key = { date ->
                                    date.id
                                }) { date ->
                                // date
                                DateView(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 32.dp
                                        )
                                        .animateItemPlacement(),
                                    name = date.name,
                                    time = date.dateStart
                                )
                            }
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
                        coroutineScope.launch {
                            modalSheetState.show()
                        }
                    }
                    .background(green)
                    .padding(16.dp),
                painter = painterResource(id = com.github.astat1cc.datebook.R.drawable.ic_add),
                contentDescription = null,
                tint = greenDark
            )
        }
    }
}