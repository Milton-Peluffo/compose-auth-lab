package com.tomildev.trakii.core.domain.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun formatDate(utcString: String?): String {
    if (utcString.isNullOrBlank()) return ""
    return try {
        val parsedDate = ZonedDateTime.parse(utcString)

        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)
            .withLocale(Locale.getDefault())

        parsedDate.format(formatter)
    } catch (e: Exception) {
        utcString ?: ""
    }
}