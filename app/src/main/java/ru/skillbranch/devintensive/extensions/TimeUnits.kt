package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Plurals

fun TimeUnits.plural(value: Int): String {
    return Plurals.getPlurals(value, this)
}