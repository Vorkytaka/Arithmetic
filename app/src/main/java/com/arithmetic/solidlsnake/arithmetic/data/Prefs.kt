package com.arithmetic.solidlsnake.arithmetic.data

import android.content.Context
import android.content.SharedPreferences
import com.arithmetic.solidlsnake.arithmetic.entity.Modes

class Prefs(context: Context) {
    private val prefs: SharedPreferences
            by lazy { context.getSharedPreferences(MODES_PREFS_NAME, Context.MODE_PRIVATE) }

    fun saveModes(modes: Modes) {
        prefs.edit()
                .putBoolean(KEY_ONES, modes.ones)
                .putBoolean(KEY_TENS, modes.tens)
                .putBoolean(KEY_HUNDRED, modes.hundreds)
                .putBoolean(KEY_SUM, modes.sum)
                .putBoolean(KEY_SUB, modes.sub)
                .putBoolean(KEY_MULTI, modes.multi)
                .apply()
    }

    fun getModes(): Modes {
        return Modes(
                ones = prefs.getBoolean(KEY_ONES, false),
                tens = prefs.getBoolean(KEY_TENS, false),
                hundreds = prefs.getBoolean(KEY_HUNDRED, false),
                sum = prefs.getBoolean(KEY_SUM, false),
                sub = prefs.getBoolean(KEY_SUB, false),
                multi = prefs.getBoolean(KEY_MULTI, false)
        )
    }

    companion object {
        private const val MODES_PREFS_NAME = "MODES_PREFS_NAME"

        // todo: maybe change to Mode.toString()?
        private const val KEY_ONES = "ONES"
        private const val KEY_TENS = "TENS"
        private const val KEY_HUNDRED = "HUNDRED"
        private const val KEY_SUM = "SUM"
        private const val KEY_SUB = "SUB"
        private const val KEY_MULTI = "MULTI"
    }
}