package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule
import com.chummerua.useCaseFileTemplatesPlugin.utils.directorySelected
import com.chummerua.useCaseFileTemplatesPlugin.utils.selectedDirectory
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.PsiDirectory
import org.jetbrains.kotlin.psi.psiUtil.parents

class NewUseCaseAction: AnAction() {

    private val PsiDirectory.parentDirectories
        get() = parents.filterIsInstance<PsiDirectory>().toList()
    private val PsiDirectory.filteredParents
        get() = parentDirectories.dropLastWhile { it.name != "com" }

    private val PsiDirectory.selectedDirectoryModule: UseCaseModule?
        get() = UseCaseModule.entries.firstOrNull { entry ->
            filteredParents.any { it.name == entry.title }
        }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isVisible = e.directorySelected && e.selectedDirectory.selectedDirectoryModule != null
        e.presentation.isEnabled = e.project != null && e.directorySelected
    }

    override fun actionPerformed(e: AnActionEvent) {
        val selectedDirectory = e.selectedDirectory
        e.project?.let { CreateUseCaseDialog(it, selectedDirectory, selectedDirectory.selectedDirectoryModule!!).show() }
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT
}
