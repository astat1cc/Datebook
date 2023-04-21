package com.github.astat1cc.datebook.datelist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.datelist.domain.DateListInteractor
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import com.github.astat1cc.datebook.datelist.presentation.models.DateListItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DateListViewModel(
    private val interactor: DateListInteractor,
    private val errorHandler: AppErrorHandler
) : ViewModel() {

    private val _dateMap =
        MutableStateFlow<UiState<Map<String, List<DateListItemUi>>>>(UiState.Loading())
    val dateMap: StateFlow<UiState<Map<String, List<DateListItemUi>>>> = _dateMap.asStateFlow()

    val hourList = listOf(
        "00:00",
        "01:00",
        "02:00",
        "03:00",
        "04:00",
        "05:00",
        "06:00",
        "07:00",
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00",
        "20:00",
        "21:00",
        "22:00",
        "23:00",
        "24:00"
    )

    init {
        with(viewModelScope) {
            launch {
                interactor.fetchDateList("").collect { fetchResult ->
                    _dateMap.value = fetchResult.toUiState()
                }
            }
        }
    }

    private fun FetchResult<Map<String, List<DateListItemDomain>>>.toUiState(): UiState<Map<String, List<DateListItemUi>>> =
        when (this) {
            is FetchResult.Success -> {
                UiState.Success(
                    data.mapValues { entry ->
                        entry.value.map { dateDomain ->
                            DateListItemUi.fromDomain(dateDomain)
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

    fun dateChanged(year: Int, month: Int, day: Int) {

    }

    fun createDate() {

    }
}