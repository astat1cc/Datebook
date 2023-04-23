package com.github.astat1cc.datebook.datedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.models.ui.DateItemUi
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.DateFormatUtil
import com.github.astat1cc.datebook.datedetails.domain.DateDetailsInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DateDetailsViewModel(
    private val dateId: Int,
    private val interactor: DateDetailsInteractor,
    private val errorHandler: AppErrorHandler,
    private val dateFormatUtil: DateFormatUtil
) : ViewModel() {

    private val _date = MutableStateFlow<UiState<DateItemUi>>(UiState.Loading())
    val date: StateFlow<UiState<DateItemUi>> = _date.asStateFlow()

    init {
        viewModelScope.launch {
            val defaultLoadingTimeJob = launch { delay(700L) }
            val date = interactor.fetchDate(dateId).toUiState()
            defaultLoadingTimeJob.join() // to improve navigating animation performance
            _date.value = date
        }
    }

    private fun FetchResult<DateItem>.toUiState(): UiState<DateItemUi> =
        when (this) {
            is FetchResult.Success -> {
                UiState.Success(
                    DateItemUi.fromDomain(data, dateFormatUtil)
                )
            }
            is FetchResult.Fail -> {
                UiState.Fail(
                    message = errorHandler.getErrorMessage(error)
                )
            }
        }
}