package ru.lnkr.todo.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun convertDateToString(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}