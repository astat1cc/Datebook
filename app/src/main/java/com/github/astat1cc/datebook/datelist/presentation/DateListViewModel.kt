package com.github.astat1cc.datebook.datelist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.astat1cc.datebook.R
import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.AppResourceProvider
import com.github.astat1cc.datebook.core.util.DateFormatUtil
import com.github.astat1cc.datebook.datelist.domain.DateListInteractor
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import com.github.astat1cc.datebook.datelist.presentation.models.DateListItemUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.*

class DateListViewModel(
    private val interactor: DateListInteractor,
    private val errorHandler: AppErrorHandler,
    private val dateFormatUtil: DateFormatUtil,
    private val appResourceProvider: AppResourceProvider
) : ViewModel() {

    private val _dateMap =
        MutableStateFlow<UiState<Map<String, List<DateListItemUi>>>>(UiState.Loading())
    val dateMap: StateFlow<UiState<Map<String, List<DateListItemUi>>>> = _dateMap.asStateFlow()

    private val _currentlyChosenDate =
        MutableStateFlow(dateFormatUtil.getDateFrom(System.currentTimeMillis()))
    val currentlyChosenDate: StateFlow<String> = _currentlyChosenDate.asStateFlow()

    private val currentlyChosenDateInMillis: StateFlow<Long> =
        _currentlyChosenDate.map { dateString -> // todo
            dateFormatUtil.getTimestampInMillisFrom(dateString)
        }.stateIn(viewModelScope, SharingStarted.Lazily, System.currentTimeMillis())

    val currentlyChosenDateInLocalDate: StateFlow<LocalDate?> =
        _currentlyChosenDate.map { dateString ->
            dateFormatUtil.getLocalDateFrom(dateString)
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _newDateTitle = MutableStateFlow<String?>(null)
    val newDateTitle: StateFlow<String?> = _newDateTitle.asStateFlow()

    private val _newDateDescription = MutableStateFlow<String?>(null)
    val newDateDescription: StateFlow<String?> = _newDateDescription.asStateFlow()

    private val _newDatePickedTime =
        MutableStateFlow(getDefaultTimeForTimePicker())

    val newDatePickedTime: StateFlow<String> = _newDatePickedTime.asStateFlow()

    private val _showNewDateTimePicker = MutableStateFlow(false)
    val showNewDateTimePicker: StateFlow<Boolean> = _showNewDateTimePicker.asStateFlow()

    init {
        with(viewModelScope) {
            launch {
                currentlyChosenDateInMillis.collect { chosenDateInMillis ->
                    collectDateListOfChosenDate(chosenDateInMillis)
                }
            }
        }
    }

    private var collectingDateListJob: Job? = null
    private suspend fun collectDateListOfChosenDate(chosenDateInMillis: Long) {
        collectingDateListJob?.cancel()
        collectingDateListJob = viewModelScope.launch {
            val dateTimestampTo =
                dateFormatUtil.getTimestampInMillisOfTheDayAfter(chosenDateInMillis)
            interactor.fetchDateList(
                dateTimestampFrom = chosenDateInMillis,
                dateTimestampTo = dateTimestampTo
            ).collect { fetchResult ->
                _dateMap.value = fetchResult.toUiState()
            }
        }
    }

    private fun FetchResult<Map<String, List<DateListItemDomain>>>.toUiState(): UiState<Map<String, List<DateListItemUi>>> =
        when (this) {
            is FetchResult.Success -> {
                UiState.Success(
                    data.mapValues { entry ->
                        entry.value.map { dateDomain ->
                            DateListItemUi.fromDomain(dateDomain, dateFormatUtil)
                        }
                    }
                )
            }
            is FetchResult.Fail -> {
                UiState.Fail(
                    message = errorHandler.getErrorMessage(error)
                )
            }
        }

    fun dateChanged(date: LocalDate) {
        _currentlyChosenDate.value = dateFormatUtil.getDateFrom(date)
    }

    fun createDate() {
        val title = _newDateTitle.value
        val description = _newDateDescription.value
        if (title.isNullOrEmpty() || description.isNullOrEmpty()) return
        viewModelScope.launch {
            interactor.saveNewDate(
                DateItem(
                    id = Random().nextInt(),
                    dateStart = dateFormatUtil.getTimestampInMillisFrom(
                        dateString = _currentlyChosenDate.value,
                        timeString = _newDatePickedTime.value
                    ),
                    dateFinish = null,
                    name = title,
                    description = description
                )
            )
            refreshDateCreatingData()
        }
    }

    private fun refreshDateCreatingData() {
        _newDateDescription.value = null
        _newDateTitle.value = null
        _newDatePickedTime.value = getDefaultTimeForTimePicker()
    }

    fun pickTime(hour: Int, minutes: Int) {
        _newDatePickedTime.value = dateFormatUtil.getTimeFrom(hour, minutes)
    }

    fun onTitleChanged(newTitle: String) {
        _newDateTitle.value = newTitle
    }

    fun onDescriptionChanged(newDescription: String) {
        _newDateDescription.value = newDescription
    }

    fun onPickTimeClicked() {
        _showNewDateTimePicker.value = true
    }

    fun timePickerIsShown() {
        _showNewDateTimePicker.value = false
    }

    private fun getDefaultTimeForTimePicker() =
        appResourceProvider.getString(R.string.default_time)

    fun canSave() =
        !(_newDateTitle.value.isNullOrEmpty() || _newDateDescription.value.isNullOrEmpty())

    fun getBlankLineToastMessage() =
        appResourceProvider.getString(R.string.lines_should_not_be_blank)

    companion object {

        val hourList = listOf(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"
        )
    }
}