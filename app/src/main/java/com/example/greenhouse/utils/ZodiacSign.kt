package com.example.greenhouse.utils

import java.util.*

object ZodiacSign {
    private val zodiacStartDates = arrayOf(
        intArrayOf(Calendar.JANUARY, 20),
        intArrayOf(Calendar.FEBRUARY, 19),
        intArrayOf(Calendar.MARCH, 21),
        intArrayOf(Calendar.APRIL, 20),
        intArrayOf(Calendar.MAY, 21),
        intArrayOf(Calendar.JUNE, 21),
        intArrayOf(Calendar.JULY, 23),
        intArrayOf(Calendar.AUGUST, 23),
        intArrayOf(Calendar.SEPTEMBER, 23),
        intArrayOf(Calendar.OCTOBER, 23),
        intArrayOf(Calendar.NOVEMBER, 22),
        intArrayOf(Calendar.DECEMBER, 22)
    )

    private val zodiacSigns = arrayOf(
        "Aquarius",
        "Pisces",
        "Aries",
        "Taurus",
        "Gemini",
        "Cancer",
        "Leo",
        "Virgo",
        "Libra",
        "Scorpio",
        "Sagittarius",
        "Capricorn"
    )

    fun getSign(date: Calendar): String {
        val month = date.get(Calendar.MONTH)
        val startMonth = zodiacStartDates.firstOrNull { it[0] == month } ?: zodiacStartDates.last()
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.YEAR, date.get(Calendar.YEAR))
        startDate.set(Calendar.MONTH, startMonth[0])
        startDate.set(Calendar.DAY_OF_MONTH, startMonth[1])
        val endDate = Calendar.getInstance()
        if (startMonth[0] == Calendar.DECEMBER) {
            endDate.set(Calendar.YEAR, date.get(Calendar.YEAR) + 1)
            endDate.set(Calendar.MONTH, Calendar.JANUARY)
        } else {
            endDate.set(Calendar.YEAR, date.get(Calendar.YEAR))
            endDate.set(Calendar.MONTH, startMonth[0] + 1)
        }
        endDate.set(Calendar.DAY_OF_MONTH, zodiacStartDates[(startMonth[0] + 1) % 12][1])

        return if (date >= startDate && date < endDate) {
            zodiacSigns[startMonth[0]]
        } else {
            zodiacSigns[(startMonth[0] + 11) % 12]
        }
    }
}