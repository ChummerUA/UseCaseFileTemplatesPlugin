package com.chummerua.useCaseFileTemplatesPlugin.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiDirectory

val AnActionEvent.directorySelected
    get() = getData(CommonDataKeys.PSI_ELEMENT) is PsiDirectory

val AnActionEvent.selectedDirectory
    get() = getData(CommonDataKeys.PSI_ELEMENT) as PsiDirectory
