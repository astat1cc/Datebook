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

    private val _dateList = MutableStateFlow<UiState<List<DateListItemUi>>>(UiState.Loading())
    val dateList: StateFlow<UiState<List<DateListItemUi>>> = _dateList.asStateFlow()

    init {
        with(viewModelScope) {
            launch {
                interactor.fetchDateList().collect { fetchResult ->
                    _dateList.value = fetchResult.toUiState()
                }
            }
        }
    }

    private fun FetchResult<List<DateListItemDomain>>.toUiState(): UiState<List<DateListItemUi>> =
        when (this) {
            is FetchResult.Success -> {
                UiState.Success(
                    data.map { dateDomain ->
                        DateListItemUi.fromDomain(dateDomain)
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
}