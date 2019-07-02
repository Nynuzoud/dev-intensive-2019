package ru.skillbranch.devintensive.utils

import android.support.annotation.VisibleForTesting
import ru.skillbranch.devintensive.extensions.*

object DateUtils {

    fun timeIntervalToString(timeInMillis: Long): String {
        if (timeInMillis < 0) {
            return when (val tempTime = timeInMillis * -1) {
                in 0 * SECOND..SECOND -> "только что"
                in SECOND..45 * SECOND -> "несколько секунд назад"
                in 45 * SECOND..75 * SECOND -> "минуту назад"
                in 75 * SECOND..45 * MINUTE -> {
                    val totalValue = tempTime / MINUTE
                    "$totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.MINUTE
                    )} назад"
                }
                in 45 * MINUTE..75 * MINUTE -> "час назад"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = tempTime / HOUR
                    "$totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.HOUR
                    )} назад"
                }
                in 22 * HOUR..26 * HOUR -> "день назад"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = tempTime / DAY
                    "$totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.DAY
                    )} назад"
                }
                else -> "более года назад"
            }
        } else {
            return when (timeInMillis) {
                in 0 * SECOND..SECOND -> "только что"
                in SECOND..45 * SECOND -> "через несколько секунд"
                in 45 * SECOND..75 * SECOND -> "через минуту"
                in 75 * SECOND..45 * MINUTE -> {
                    val totalValue = timeInMillis / MINUTE
                    "через $totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.MINUTE
                    )}"
                }
                in 45 * MINUTE..75 * MINUTE -> "через час"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = timeInMillis / HOUR
                    "через $totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.HOUR
                    )}"
                }
                in 22 * HOUR..26 * HOUR -> "через день"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = timeInMillis / DAY
                    "через $totalValue ${getDatePlurals(
                        totalValue,
                        TimeUnits.DAY
                    )}"
                }
                else -> "более чем через год"
            }
        }
    }

    @VisibleForTesting
    fun getDatePlurals(totalValue: Long, timeUnits: TimeUnits): String {
        return when {
            totalValue in 11L..20L -> getManyPlural(timeUnits)
            (Math.abs(totalValue) % 10) in 2L..4L -> get2Plural(
                timeUnits
            )
            else -> getManyPlural(timeUnits)
        }
    }

    private fun get2Plural(timeUnits: TimeUnits): String {
        return when (timeUnits) {
            TimeUnits.SECOND -> "секунды"
            TimeUnits.MINUTE -> "минуты"
            TimeUnits.HOUR -> "часа"
            TimeUnits.DAY -> "дня"
        }
    }

    private fun getManyPlural(timeUnits: TimeUnits): String {
        return when (timeUnits) {
            TimeUnits.SECOND -> "секунд"
            TimeUnits.MINUTE -> "минут"
            TimeUnits.HOUR -> "часов"
            TimeUnits.DAY -> "дней"
        }
    }
}