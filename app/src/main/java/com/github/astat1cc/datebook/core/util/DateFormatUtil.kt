package com.github.astat1cc.datebook.core.util

interface DateFormatUtil {

    fun getOnlyDateFrom(fullDate: String): String

    fun getOnlyTimeFrom(fullDate: String): String

    class Impl() : DateFormatUtil {

        override fun getOnlyDateFrom(fullDate: String) =
            fullDate.substringBefore(" ")

        override fun getOnlyTimeFrom(fullDate: String) =
            fullDate.substringAfter(" ")
    }
}