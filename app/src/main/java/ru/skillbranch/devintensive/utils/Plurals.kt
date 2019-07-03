package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.TimeUnits

object Plurals {

    fun getPlurals(totalValue: Int, timeUnits: TimeUnits): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(totalValue)
        stringBuilder.append(" ")
        val pluralResult = when {
            (Math.abs(totalValue) % 10) == 1 -> get1Plural(timeUnits)
            totalValue in 11..20 -> getManyPlural(timeUnits)
            (Math.abs(totalValue) % 10) in 2..4 -> get2Plural(
                timeUnits
            )
            else -> getManyPlural(timeUnits)
        }
        stringBuilder.append(pluralResult)
        return stringBuilder.toString()
    }

    private fun get1Plural(timeUnits: TimeUnits): String {
        return when(timeUnits) {
            TimeUnits.SECOND -> "секунду"
            TimeUnits.MINUTE -> "минуту"
            TimeUnits.HOUR -> "час"
            TimeUnits.DAY -> "день"
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