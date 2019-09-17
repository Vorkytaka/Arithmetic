package com.arithmetic.solidlsnake.arithmetic

import android.app.Application
import com.arithmetic.solidlsnake.arithmetic.ioc.AppModule
import com.arithmetic.solidlsnake.arithmetic.ioc.AppModuleImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppModule.instance = AppModuleImpl(this)
    }
}