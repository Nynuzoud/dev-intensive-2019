package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.trimOrNull
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? =
            if (fullName?.isNotEmpty() == true) fullName.split(" ") else null

        val firstName = parts?.getOrNull(0)?.trimOrNull()
        val lastName = parts?.getOrNull(1)?.trimOrNull()

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val stringBuilder = StringBuilder()
        payload.toCharArray().forEach {
            if (it.isWhitespace()) {
                stringBuilder.append(divider)
            } else {
                if (it.isUpperCase()) {
                    stringBuilder.append(
                        Transliterator.rusToEngByLetter(it.toLowerCase().toString())
                            .toUpperCase(Locale("ru"))
                    )
                } else {
                    stringBuilder.append(
                        Transliterator.rusToEngByLetter(
                            it.toString()
                        )
                    )
                }
            }
        }

        return stringBuilder.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {

        val firstInitial = firstName.trimOrNull()?.first()?.toString() ?: ""
        val secondInitial = lastName.trimOrNull()?.first()?.toString() ?: ""

        return if (firstInitial.isEmpty() && secondInitial.isEmpty()) {
            null
        } else {
            "$firstInitial$secondInitial"
        }
    }
}