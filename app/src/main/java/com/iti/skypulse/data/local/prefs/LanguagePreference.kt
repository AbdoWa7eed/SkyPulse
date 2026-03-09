package com.iti.skypulse.data.local.prefs

import android.content.Context
import com.iti.skypulse.core.utils.Language
import androidx.core.content.edit

object LanguagePreference {
    private const val PREFS_NAME = "lang_prefs"
    private const val KEY_LANGUAGE = "language"

    fun getLanguageCode(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LANGUAGE, Language.ENGLISH.code) ?: Language.ENGLISH.code
    }

    fun saveLanguageCode(context: Context, code: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit { putString(KEY_LANGUAGE, code) }
    }
}