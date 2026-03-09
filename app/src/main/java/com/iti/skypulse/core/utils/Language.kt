package com.iti.skypulse.core.utils

import androidx.annotation.StringRes
import com.iti.skypulse.R

enum class Language(
    val code: String,
    @param:StringRes val labelRes: Int
) {
    ENGLISH("en", R.string.english),
    ARABIC("ar", R.string.arabic);

    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.code == code } ?: ENGLISH
        }
    }
}