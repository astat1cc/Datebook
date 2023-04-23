package com.github.astat1cc.datebook.datelist.presentation

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.astat1cc.datebook.R
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.ui.colors.dateListScreenBackground
import com.github.astat1cc.datebook.core.ui.colors.green
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.datelist.presentation.views.DateCreatingSheet
import com.github.astat1cc.datebook.datelist.presentation.views.DateView
import com.github.astat1cc.datebook.datelist.presentation.views.HourIntervalView
import com.github.astat1cc.datebook.datelist.presentation.views.HourView
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun DateListScreen(
    onNavigateToDateDetails: (Int) -> Unit,
    viewModel: DateListViewModel = getViewModel()
) {

    val dateMapState = viewModel.dateMap.collectAsState()
    val currentlyChosenDateState = viewModel.currentlyChosenDate.collectAsState()
    val currentlyChosenDateInLocalDate = viewModel.currentlyChosenDateInLocalDate.collectAsState()
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
        animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing)
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

    val interactionSource = remember { MutableInteractionSource() }

    if (showNewDateTimePicker.value) {
        timePickerDialog.show()
        viewModel.timePickerIsShown()
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    // For hiding the keyboard & removing the Focus on hiding the sheet.
    LaunchedEffect(key1 = modalSheetState.currentValue) {
        if (modalSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

    // for hiding the keyboard & removing the focus on hiding the sheet
    LaunchedEffect(key1 = modalSheetState.currentValue) {
        if (modalSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
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
                        if (viewModel.canSave()) {
                            modalSheetState.hide()
                            viewModel.createDate()
                        } else {
                            Toast.makeText(
                                context,
                                viewModel.getBlankLineToastMessage(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                onPickTimeClicked = {
                    viewModel.onPickTimeClicked()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(dateListScreenBackground)
        ) {
            Kalendar(
                modifier = Modifier
                    .fillMaxWidth(),
                onCurrentDayClick = { date, _ ->
                    viewModel.dateChanged(date.localDate)
                },
                takeMeToDate = currentlyChosenDateInLocalDate.value,
                kalendarDayColors = KalendarDayColors(
                    textColor = greenDark,
                    selectedTextColor = Color.White
                ),
                kalendarThemeColor = KalendarThemeColor(
                    backgroundColor = Color.White,
                    dayBackgroundColor = greenDark,
                    headerTextColor = greenDark
                )
            )
            Box {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                    when (dateMap) {
                        is UiState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 40.dp)
                                        .fillMaxSize(),
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
                                ) {
                                    HourIntervalView(
                                        modifier = Modifier.animateItemPlacement(),
                                        hour = hour
                                    )
                                }
                                items(
                                    items = dateMap.data[hour] ?: emptyList(),
                                    key = { date ->
                                        date.id
                                    }) { date ->
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                vertical = 12.dp,
                                                horizontal = 32.dp
                                            )
                                            .animateItemPlacement()
                                    ) {
                                        // invisible view to align DateView below the line
                                        HourView(modifier = Modifier.alpha(0f), hour = hour)
                                        // date
                                        DateView(
                                            modifier = Modifier
                                                .padding(start = 24.dp)
                                                .clickable(
                                                    interactionSource = interactionSource,
                                                    indication = null
                                                ) {
                                                    onNavigateToDateDetails(date.id)
                                                },
                                            name = date.name,
                                            time = date.dateStart
                                        )
                                    }
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
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = greenDark
                )
            }
        }
    }
}