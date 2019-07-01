package ru.skillbranch.devintensive.extensions

fun String?.trimOrNull(): String? {
    val resultString = this?.trim()

    return if (resultString?.isEmpty() == true) null else resultString
}