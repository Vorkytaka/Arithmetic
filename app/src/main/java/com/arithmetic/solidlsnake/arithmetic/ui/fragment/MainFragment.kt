package com.arithmetic.solidlsnake.arithmetic.ui.fragment

import android.os.Bundle
import android.view.View
import com.arithmetic.solidlsnake.arithmetic.R
import com.arithmetic.solidlsnake.arithmetic.entity.Mode
import com.arithmetic.solidlsnake.arithmetic.feature.MainFeature
import com.arithmetic.solidlsnake.arithmetic.ioc.activityModule
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main

    private val feature = activityModule().mainFeature

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feature.subscribe(::render)

        btn_sum_mode.setOnClickListener(::modeClickListener)
        btn_sub_mode.setOnClickListener(::modeClickListener)
        btn_multi_mode.setOnClickListener(::modeClickListener)
        btn_ones_mode.setOnClickListener(::modeClickListener)
        btn_tens_mode.setOnClickListener(::modeClickListener)
        btn_hundreds_mode.setOnClickListener(::modeClickListener)

        btn_start.setOnClickListener { feature.accept(MainFeature.Action.StartGame) }
    }

    private fun render(state: MainFeature.State) {
        btn_sum_mode.isSelected = state.modes.sum
        btn_sub_mode.isSelected = state.modes.sub
        btn_multi_mode.isSelected = state.modes.multi
        btn_ones_mode.isSelected = state.modes.ones
        btn_tens_mode.isSelected = state.modes.tens
        btn_hundreds_mode.isSelected = state.modes.hundreds

        btn_start.visibility = if (state.canStart) View.VISIBLE else View.GONE
    }

    private fun modeClickListener(view: View) {
        val clicked = Mode.valueOf(view.tag as String)
        feature.accept(MainFeature.Action.ClickedOnMode(clicked))
    }
}