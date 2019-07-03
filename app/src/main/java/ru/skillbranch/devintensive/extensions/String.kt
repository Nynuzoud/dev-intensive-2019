package ru.skillbranch.devintensive.extensions

fun String?.trimOrNull(): String? {
    val resultString = this?.trim()

    return if (resultString?.isEmpty() == true) null else resultString
}

fun String?.truncate(count: Int = 16): String? {
    return this?.take(count)?.trim().plus("...")
}

fun String?.stripHtml(): String? {
    return this
        ?.replace("<.*?>".toRegex(),"")
        ?.replace("\\s+".toRegex(), " ")
}