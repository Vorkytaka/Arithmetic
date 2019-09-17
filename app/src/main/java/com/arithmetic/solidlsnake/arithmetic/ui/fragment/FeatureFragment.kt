package com.arithmetic.solidlsnake.arithmetic.ui.fragment

import android.os.Bundle
import android.view.View
import com.arithmetic.solidlsnake.arithmetic.common.IFeature

abstract class FeatureFragment<State, Action, Effect> : BaseFragment() {
    abstract val feature: IFeature<State, Action, Effect>

    abstract fun render(state: State)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feature.subscribe(::render)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (requireActivity().isFinishing) {
            feature.unsubscribeAll()
        }
    }
}