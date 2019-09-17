package com.arithmetic.solidlsnake.arithmetic.ioc

import android.annotation.SuppressLint
import android.content.Context
import com.arithmetic.solidlsnake.arithmetic.data.Prefs

interface AppModule {
    val prefs: Prefs

    companion object {
        lateinit var instance: AppModule
    }
}

@SuppressLint("StaticFieldLeak")
class AppModuleImpl(
        cxt: Context
) : AppModule {

    override val prefs: Prefs by lazy { Prefs(context) }
    private val context: Context = cxt.applicationContext
}