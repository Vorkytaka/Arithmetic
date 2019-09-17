package com.arithmetic.solidlsnake.arithmetic.ioc

import com.arithmetic.solidlsnake.arithmetic.common.IFeature
import com.arithmetic.solidlsnake.arithmetic.feature.MainFeature
import com.arithmetic.solidlsnake.arithmetic.ui.Router

fun activityModule(): ActivityModule = ActivityModule.instance!!

interface ActivityModule {
    val mainFeature: IFeature<MainFeature.State, MainFeature.Action, MainFeature.Effect>
    val router: Router

    companion object {
        var instance: ActivityModule? = null
    }
}

class ActivityModuleImpl(
        private val appModule: AppModule,
        override val router: Router
) : ActivityModule {
    override val mainFeature: IFeature<MainFeature.State, MainFeature.Action, MainFeature.Effect>
        get() = MainFeature.feature(appModule.prefs, router)
}