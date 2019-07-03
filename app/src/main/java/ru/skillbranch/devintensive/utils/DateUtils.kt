package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.*

object DateUtils {

    fun timeIntervalToString(timeInMillis: Long): String {
        if (timeInMillis < 0) {
            return when (val tempTime = timeInMillis * -1) {
                in 0 * SECOND..SECOND -> "только что"
                in SECOND..45 * SECOND -> "несколько секунд назад"
                in 45 * SECOND..75 * SECOND -> "минуту назад"
                in 75 * SECOND..45 * MINUTE -> {
                    val totalValue = (tempTime / MINUTE).toInt()
                    "${TimeUnits.MINUTE.plural(totalValue)} назад"
                }
                in 45 * MINUTE..75 * MINUTE -> "час назад"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = (tempTime / HOUR).toInt()
                    "${TimeUnits.HOUR.plural(totalValue)} назад"
                }
                in 22 * HOUR..26 * HOUR -> "день назад"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = (tempTime / DAY).toInt()
                    "${TimeUnits.DAY.plural(totalValue)} назад"
                }
                else -> "более года назад"
            }
        } else {
            return when (timeInMillis) {
                in 0 * SECOND..SECOND -> "только что"
                in SECOND..45 * SECOND -> "через несколько секунд"
                in 45 * SECOND..75 * SECOND -> "через минуту"
                in 75 * SECOND..45 * MINUTE -> {
                    val totalValue = (timeInMillis / MINUTE).toInt()
                    "через ${TimeUnits.MINUTE.plural(totalValue)}"
                }
                in 45 * MINUTE..75 * MINUTE -> "через час"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = (timeInMillis / HOUR).toInt()
                    "через ${TimeUnits.HOUR.plural(totalValue)}"
                }
                in 22 * HOUR..26 * HOUR -> "через день"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = (timeInMillis / DAY).toInt()
                    "через ${TimeUnits.DAY.plural(totalValue)}"
                }
                else -> "более чем через год"
            }
        }
    }
}