package ru.skillbranch.devintensive.utils

import android.content.Context
import ru.skillbranch.devintensive.extensions.trimOrNull
import java.util.*
import kotlin.math.roundToInt

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

        val firstInitial =
            firstName.trimOrNull()?.first()?.toString()?.toUpperCase(Locale("ru")) ?: ""
        val secondInitial =
            lastName.trimOrNull()?.first()?.toString()?.toUpperCase(Locale("ru")) ?: ""

        return if (firstInitial.isEmpty() && secondInitial.isEmpty()) {
            null
        } else {
            "$firstInitial$secondInitial"
        }
    }

    fun repoIsValid(s: CharSequence?): Boolean {
        if (s == null) return true

        var url = s.toString().toLowerCase(Locale("ru"))
        val validPrefixes = arrayOf("https://www.", "https://", "www.")
        val validDomen = "github.com"
        val exceptions = arrayOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join"
        )

        validPrefixes.forEach {
            url = url.replaceFirst(it, "")
        }

        exceptions.forEach {
            url = url.replaceFirst(it, "")
        }

        url = url
            .replaceFirst(validDomen, "")
            .replaceFirst("/", "").let {
                if (it.isNotEmpty() && it[it.length - 1] == '/') {
                    return@let it.substring(0..it.length - 2)
                } else return@let it
            }
        if (url.isEmpty()) return false

        return url.matches("[a-zA-Z0-9_-]*".toRegex())
    }

    fun convertPxToDp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).roundToInt()
    }

    fun convertDpToPx(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).roundToInt()
    }

}