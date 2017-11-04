package com.dleal.moviedb.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by daniel.leal on 04.11.17.
 */
fun parseStringToDate(dateString: String, language: String = DEFAULT_LANGUAGE): Date {
    val locale = Locale(language)
    val simpleDateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, locale)
    return simpleDateFormat.parse(dateString)
}

fun dateToString(date: Date, language: String = DEFAULT_LANGUAGE): String {
    val locale = Locale(language)
    val simpleDateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, locale)
    return simpleDateFormat.format(date)
}