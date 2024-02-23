package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class UseCaseConfig(
    var name: String = "",
    var useCaseType: UseCaseType = UseCaseType.EXECUTABLE,
    var overrideDispatcher: Boolean = false,
    var dispatcher: CoroutineContext = Dispatchers.Default,
    var input: Any? = null,
    var output: Any? = null
) {
    val canOverrideDispatcher = false
}
