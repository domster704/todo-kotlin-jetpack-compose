package ru.lnkr.todo.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Singleton class for some utility methods
 */
object Util {
    /**
     * Convert date to string
     * @param date[Date]
     * @return date as string in format dd.MM.yyyy (for example, 20.12.2017)
     */
    fun convertDateToString(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}