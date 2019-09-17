package com.arithmetic.solidlsnake.arithmetic.feature

import com.arithmetic.solidlsnake.arithmetic.common.ExecutorEffectHandler
import com.arithmetic.solidlsnake.arithmetic.common.Feature
import com.arithmetic.solidlsnake.arithmetic.common.IFeature
import com.arithmetic.solidlsnake.arithmetic.common.Reducer
import com.arithmetic.solidlsnake.arithmetic.data.Prefs
import com.arithmetic.solidlsnake.arithmetic.entity.Mode
import com.arithmetic.solidlsnake.arithmetic.entity.Modes
import com.arithmetic.solidlsnake.arithmetic.ui.Router

object MainFeature {
    fun feature(prefs: Prefs, router: Router): IFeature<State, Action, Effect> =
            Feature(
                    State(Modes(ones = false, tens = false, hundreds = false, sum = false, sub = false, multi = false)),
                    MainFeature.Effect.Get,
                    reducer = reducer,
                    effectHandler = EffectHandler(prefs, router)
            )

    data class State(
            val modes: Modes
    ) {
        val canStart = (modes.ones || modes.tens || modes.hundreds) && (modes.sum || modes.sub || modes.multi)
    }

    sealed class Action {
        data class ClickedOnMode(val mode: Mode) : Action()
        data class Loaded(val modes: Modes) : Action()
        object StartGame : Action()
    }

    sealed class Effect {
        data class Save(val modes: Modes) : Effect()
        object Get : Effect()
        object Start : Effect()
    }

    class EffectHandler(
            private val prefs: Prefs,
            private val router: Router
    ) : ExecutorEffectHandler<Effect, Action>() {
        override fun invoke(eff: Effect): () -> Action? {
            return when (eff) {
                is Effect.Save -> ({
                    prefs.saveModes(eff.modes)
                    MainFeature.Action.Loaded(eff.modes)
                })

                is Effect.Get -> ({
                    val modes = prefs.getModes()
                    MainFeature.Action.Loaded(modes)
                })

                is Effect.Start -> ({
                    router.goToGameScreen()
                    null
                })
            }
        }
    }

    private val reducer: Reducer<State, Action, Effect> = { state, action ->
        when (action) {
            is Action.Loaded -> state.copy(modes = action.modes) to null
            is Action.StartGame -> state to setOf(MainFeature.Effect.Start)
            is Action.ClickedOnMode -> {
                val newModes: Modes = when (action.mode) {
                    Mode.ONES -> state.modes.copy(ones = !state.modes.ones)
                    Mode.TENS -> state.modes.copy(tens = !state.modes.tens)
                    Mode.HUNDREDS -> state.modes.copy(hundreds = !state.modes.hundreds)
                    Mode.SUM -> state.modes.copy(sum = !state.modes.sum)
                    Mode.SUB -> state.modes.copy(sub = !state.modes.sub)
                    Mode.MULTI -> state.modes.copy(multi = !state.modes.multi)
                }
                state to setOf(MainFeature.Effect.Save(newModes))
            }
        }
    }
}