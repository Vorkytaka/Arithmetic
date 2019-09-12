package com.arithmetic.solidlsnake.arithmetic.common

import java.util.concurrent.Executors

typealias Reducer<State, Action, Effect> = (State, Action) -> Pair<State, Set<Effect>?>

interface IEffectHandler<Effect, Action> {
    fun perform(eff: Effect)

    fun subscribe(listener: (Action) -> Unit)
    fun unsubscribeAll()
}

interface IFeature<State, Action, Effect> {
    val currentState: State
    fun accept(message: Action)

    fun subscribe(listener: (State) -> Unit)
    fun unsubscribeAll()
}

class Feature<State, Action, Effect>(
    initState: State,
    starter: Effect?,
    private val reducer: Reducer<State, Action, Effect>,
    private val effectHandler: IEffectHandler<Effect, Action>
) : IFeature<State, Action, Effect> {

    init {
        starter?.let(effectHandler::perform)
        effectHandler.subscribe(::accept)
    }

    private val listeners = HashSet<(State) -> Unit>()
    private var _state: State = initState
        set(value) {
            listeners.forEach { it(value) }
            field = value
        }

    override val currentState: State
        get() = _state

    override fun accept(message: Action) {
        val (newState, effects) = reducer(_state, message)
        _state = newState
        handle(effects)
    }

    override fun subscribe(listener: (State) -> Unit) {
        listeners.add(listener)
    }

    override fun unsubscribeAll() {
        effectHandler.unsubscribeAll()
        listeners.clear()
    }

    private fun handle(effects: Set<Effect>?) {
        effects?.forEach(effectHandler::perform)
    }

}

abstract class ExecutorEffectHandler<Effect, Action> : IEffectHandler<Effect, Action> {
    private val listeners = HashSet<(Action) -> Unit>()
    private val executors = Executors.newSingleThreadExecutor()

    abstract operator fun invoke(eff: Effect): () -> Action

    override fun perform(eff: Effect) {
        val future = executors.submit(this(eff))
        val value = future.get()
        listeners.forEach { it(value) }
    }

    override fun subscribe(listener: (Action) -> Unit) {
        listeners.add(listener)
    }

    override fun unsubscribeAll() {
        // todo: cancel futures
        listeners.clear()
    }
}