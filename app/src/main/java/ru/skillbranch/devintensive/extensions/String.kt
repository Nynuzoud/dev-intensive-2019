package ru.skillbranch.devintensive.extensions

fun String?.trimOrNull(): String? {
    val resultString = this?.trim()

    return if (resultString?.isEmpty() == true) null else resultString
}

fun String?.truncate(count: Int = 16): String? {
    if (count == this?.length) return this

    return this
        ?.trim()
        ?.replace("\\s+".toRegex(), " ")
        ?.take(count)
        ?.trim()
        ?.let {
            return when {
                it.length > 1  -> it.plus("...")
                it.length == 1 -> it
                else -> ""
            }
        }
}

fun String?.stripHtml(): String? {
    return this
        ?.replace("<.*?>".toRegex(), "")
        ?.replace("\\s+".toRegex(), " ")
}