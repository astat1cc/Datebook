package com.github.astat1cc.datebook.core.util

import com.github.astat1cc.datebook.R
import com.github.astat1cc.datebook.core.models.domain.ErrorType

interface AppErrorHandler {

    fun getErrorMessage(error: ErrorType): String

    fun getErrorTypeOf(exception: Exception): ErrorType

    class Impl(
        private val resources: AppResourceProvider
    ) : AppErrorHandler {

        override fun getErrorMessage(error: ErrorType): String =
            when (error) {
                is ErrorType.Generic -> error.message
                    ?: resources.getString(R.string.something_went_wrong)
            }

        override fun getErrorTypeOf(exception: Exception): ErrorType =
            ErrorType.Generic(exception.message)
    }
}