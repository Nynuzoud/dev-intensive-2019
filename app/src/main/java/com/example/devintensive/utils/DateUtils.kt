package com.example.devintensive.utils

import android.support.annotation.VisibleForTesting
import com.example.devintensive.extensions.*

object DateUtils {

    fun timeIntervalToString(timeInMillis: Long): String {
        if (timeInMillis < 0) {
            return when (val tempTime = timeInMillis * -1) {
                in 0 * SECOND..SECOND -> "только что"
                in SECOND..45 * SECOND -> "несколько секунд назад"
                in 45 * SECOND..75 * SECOND -> "минуту назад"
                in 75 * SECOND..45 * MINUTE -> {
                    val totalValue = tempTime / MINUTE
                    "$totalValue ${getDatePlurals(totalValue, TimeUnit.MINUTE)} назад"
                }
                in 45 * MINUTE..75 * MINUTE -> "час назад"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = tempTime / HOUR
                    "$totalValue ${getDatePlurals(totalValue, TimeUnit.HOUR)} назад"
                }
                in 22 * HOUR..26 * HOUR -> "день назад"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = tempTime / DAY
                    "$totalValue ${getDatePlurals(totalValue, TimeUnit.DAY)} назад"
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
                    "через $totalValue ${getDatePlurals(totalValue, TimeUnit.MINUTE)}"
                }
                in 45 * MINUTE..75 * MINUTE -> "через час"
                in 75 * MINUTE..22 * HOUR -> {
                    val totalValue = timeInMillis / HOUR
                    "через $totalValue ${getDatePlurals(totalValue, TimeUnit.HOUR)}"
                }
                in 22 * HOUR..26 * HOUR -> "через день"
                in 26 * HOUR..360 * DAY -> {
                    val totalValue = timeInMillis / DAY
                    "через $totalValue ${getDatePlurals(totalValue, TimeUnit.DAY)}"
                }
                else -> "более чем через год"
            }
        }
    }

    @VisibleForTesting
    fun getDatePlurals(totalValue: Long, timeUnit: TimeUnit): String {
        return when {
            totalValue in 11L..20L -> getManyPlural(timeUnit)
            (Math.abs(totalValue) % 10) in 2L..4L -> get2Plural(timeUnit)
            else -> getManyPlural(timeUnit)
        }
    }

    private fun get2Plural(timeUnit: TimeUnit): String {
        return when (timeUnit) {
            TimeUnit.SECOND -> "секунды"
            TimeUnit.MINUTE -> "минуты"
            TimeUnit.HOUR -> "часа"
            TimeUnit.DAY -> "дня"
        }
    }

    private fun getManyPlural(timeUnit: TimeUnit): String {
        return when (timeUnit) {
            TimeUnit.SECOND -> "секунд"
            TimeUnit.MINUTE -> "минут"
            TimeUnit.HOUR -> "часов"
            TimeUnit.DAY -> "дней"
        }
    }
}