package com.github.astat1cc.datebook.core.util

import android.util.Log
import kotlinx.datetime.LocalDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface DateFormatUtil {

    fun getOnlyDateFrom(fullDate: String): String

    fun getOnlyHourFrom(timestampMillis: Long): String

    fun getDateFrom(year: Int, month: Int, day: Int): String // todo

    fun getDateWithTimeFrom(timestampMillis: Long): String

    fun getTimeFrom(hour: Int, minutes: Int): String

    fun getDateFrom(timestampMillis: Long): String

    fun getTimestampInMillisFrom(dateString: String): Long

    fun getTimestampInMillisFrom(dateString: String, timeString: String): Long

    fun getTimestampInMillisOfTheDayAfter(chosenDateInMillis: Long): Long

    fun getDateFrom(kalendarDate: LocalDate): String

    fun getLocalDateFrom(dateString: String): LocalDate

    fun getHourAndMinutesPairFrom(dateString: String): Pair<Int, Int>

    class Impl : DateFormatUtil {

        override fun getOnlyDateFrom(fullDate: String) =
            fullDate.substringBefore(" ")

        override fun getOnlyHourFrom(timestampMillis: Long): String {
            val formatter: DateFormat = SimpleDateFormat("HH", Locale.getDefault())
            return formatter.format(Date(timestampMillis))
        }

        override fun getDateFrom(year: Int, month: Int, day: Int) =
            "${if (day < 10) "0" else ""}$day.${if (month < 10) "0" else ""}${month + 1}.$year" // month + 1 because months start from 0

        override fun getTimeFrom(hour: Int, minutes: Int) =
            "${if (hour < 10) "0" else ""}$hour:${if (minutes < 10) "0" else ""}$minutes"

        override fun getDateFrom(timestampMillis: Long): String {
            val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return formatter.format(Date(timestampMillis))
        }

        override fun getDateFrom(kalendarDate: LocalDate): String {
            val (year, month, day) = kalendarDate.toString().split("-")
            return "$day.$month.$year"
        }

        override fun getDateWithTimeFrom(timestampMillis: Long): String {
            val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            return formatter.format(Date(timestampMillis))
        }

        override fun getTimestampInMillisFrom(dateString: String): Long {
            val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return formatter.parse(dateString)?.time ?: System.currentTimeMillis()
        }

        override fun getTimestampInMillisFrom(dateString: String, timeString: String): Long {
            val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            return formatter.parse("$dateString $timeString")?.time ?: System.currentTimeMillis()
        }

        override fun getTimestampInMillisOfTheDayAfter(chosenDateInMillis: Long): Long =
            chosenDateInMillis + DAY_IN_MILLIS

        override fun getLocalDateFrom(dateString: String): LocalDate {
            val (day, month, year) = dateString.split(".").map { numString ->
                tryToConvertToInt(numString)
            }
            return LocalDate(year, month, day)
        }

        override fun getHourAndMinutesPairFrom(dateString: String): Pair<Int, Int> {
            val (hour, minutes) = dateString.substringAfterLast(" ").split(":")
                .map { numString -> tryToConvertToInt(numString) }
            return hour to minutes
        }

        private fun tryToConvertToInt(numString: String) = try {
            numString.toInt()
        } catch (e: Exception) {
            0
        }

        companion object {

            const val DAY_IN_MILLIS = 86400000L
        }
    }
}