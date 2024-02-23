package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.utils.directorySelected
import com.chummerua.useCaseFileTemplatesPlugin.utils.selectedDirectory
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewUseCaseAction: AnAction() {
    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isVisible = e.directorySelected
        e.presentation.isEnabled = e.project != null && e.directorySelected // TODO ensure modules are valid
    }

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { CreateUseCaseDialog(it, e.selectedDirectory).show() }
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT
}
