package com.github.astat1cc.datebook.core.models.domain

sealed class ErrorType {

    class Generic(val message: String?) : ErrorType()
}