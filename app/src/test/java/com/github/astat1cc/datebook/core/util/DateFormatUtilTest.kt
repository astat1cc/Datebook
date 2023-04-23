package com.github.astat1cc.datebook.core.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateFormatUtilTest {

    private val testDateFormatUtil = DateFormatUtil.Impl()

    @Test
    fun `formatting full date to only date is correct`() {
        val actual = testDateFormatUtil.getOnlyDateFrom("23.04.2023 20:44")
        val expected = "23.04.2023"

        assertEquals(expected, actual)
    }

    @Test
    fun `formatting timestamp in millis to full date with time is correct`() {
        val timestampInMillis = System.currentTimeMillis()

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestampInMillis
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val actual = testDateFormatUtil.getDateWithTimeFrom(timestampInMillis)
        val expected =
            "${if (day < 10) "0" else ""}$day.${if (month < 10) "0" else ""}${month + 1}.$year " +
                    "${if (hour < 10) "0" else ""}$hour:${if (minute < 10) "0" else ""}$minute"
        assertEquals(expected, actual)
    }
}