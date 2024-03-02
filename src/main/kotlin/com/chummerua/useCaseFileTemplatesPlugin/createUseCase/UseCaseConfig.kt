package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.intellij.psi.PsiElement
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class UseCaseConfig(
    var useCaseType: UseCaseType,
    var useCaseName: String = "",
    var overrideDispatcher: Boolean = false,
    var dispatcher: CoroutineContext = Dispatchers.Default,
    var inputPsi: PsiElement? = null,
    var input: String = "",
    var outputPsi: PsiElement? = null,
    var output: String = ""
)
